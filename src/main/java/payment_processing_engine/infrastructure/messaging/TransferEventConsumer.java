package payment_processing_engine.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import payment_processing_engine.domain.service.PaymentTransferService;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class TransferEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(TransferEventConsumer.class);
    private final PaymentTransferService paymentTransferService;

    public TransferEventConsumer(PaymentTransferService paymentTransferService) {
        this.paymentTransferService = paymentTransferService;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.transfer-intents:payment.transfer.intents}",
            groupId = "${app.kafka.consumer-groups.engine:payment-engine-core}",
            properties = {"spring.json.value.default.type=payment_processing_engine.infrastructure.messaging.TransferEventConsumer$TransferEventPayload"}
    )
    public void consumeTransferIntent(@Payload TransferEventPayload payload,
                                      @Header("Kafka_receivedMessageKey") String messageKey,
                                      Acknowledgment acknowledgment) {

        log.info("Received transfer payload automatically deserialized. Key: {}", messageKey);

        try {
            paymentTransferService.executeTransfer(
                    payload.userId(),
                    payload.sourceAccountId(),
                    payload.targetAccountId(),
                    payload.amount(),
                    payload.sourceCurrency(),
                    payload.targetCurrency(),
                    payload.fxRate(),
                    payload.idempotencyKey(),
                    payload.routingDetails()
            );

            log.info("Asynchronous transaction context successfully verified. Key: {}", messageKey);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Critical error processing transaction message stream. Key: {}. Reason: {}", messageKey, e.getMessage());
        }
    }

    private record TransferEventPayload(
            UUID userId,
            UUID sourceAccountId,
            UUID targetAccountId,
            BigDecimal amount,
            String sourceCurrency,
            String targetCurrency,
            BigDecimal fxRate,
            String idempotencyKey,
            String routingDetails
    ) {}
}