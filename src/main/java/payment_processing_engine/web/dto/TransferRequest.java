package payment_processing_engine.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        @NotNull UUID userId,
        @NotNull UUID sourceAccountId,
        @NotNull UUID targetAccountId,
        @NotNull @Positive BigDecimal amount,
        @NotBlank String sourceCurrency,
        @NotBlank String targetCurrency,
        @NotNull @Positive BigDecimal fxRate,
        @NotBlank String routingDetails
) {}