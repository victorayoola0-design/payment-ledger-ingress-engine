package payment_processing_engine.infrastructure.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import payment_processing_engine.domain.exception.DomainException;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class FxRateClient {

    private static final Logger log = LoggerFactory.getLogger(FxRateClient.class);
    private final RestClient restClient;

    public FxRateClient(RestClient.Builder restClientBuilder,
                        @Value("${app.integration.fx-service.base-url:https://api.fx-desk.internal}") String baseUrl) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public BigDecimal fetchLiveRate(String sourceCurrency, String targetCurrency) {
        log.info("Fetching wholesale FX rate from external desk: {} -> {}", sourceCurrency, targetCurrency);

        try {
            FxResponse response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/rates")
                            .queryParam("base", sourceCurrency)
                            .queryParam("target", targetCurrency)
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        log.error("Client error received from FX desk payload: {}", res.getStatusCode());
                        throw new DomainException("Invalid parameters sent to FX engine.");
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        log.error("Wholesale FX desk returned a critical server fault.");
                        throw new DomainException("Wholesale FX platform is currently unreachable.");
                    })
                    .body(FxResponse.class);

            if (response == null || response.rate() == null) {
                throw new DomainException("FX engine returned an empty or malformed rate payload.");
            }

            log.info("Successfully resolved FX rate: {} (Source: External FX Desk)", response.rate());
            return response.rate();

        } catch (Exception e) {
            log.error("Failed downstream connection to foreign exchange service: {}", e.getMessage());
            throw new DomainException("Unable to process currency conversion factors: " + e.getMessage());
        }
    }
    private record FxResponse(String base, String target, BigDecimal rate, Map<String, Object> metadata) {}
}