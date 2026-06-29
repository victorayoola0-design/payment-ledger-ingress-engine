package payment_processing_engine.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Transfer {
    private final UUID id;
    private final UUID userId;
    private final String sourceCurrency;
    private final String targetCurrency;
    private final BigDecimal sourceAmount;
    private final BigDecimal targetAmount;
    private final BigDecimal fxRate;
    private TransferStatus status;
    private final String idempotencyKey;
    private final String recipientRoutingDetails; // JSON string payload
    private OffsetDateTime updatedAt;
    private final OffsetDateTime createdAt;

    public Transfer(UUID id, UUID userId, String sourceCurrency, String targetCurrency,
                    BigDecimal sourceAmount, BigDecimal targetAmount, BigDecimal fxRate,
                    TransferStatus status, String idempotencyKey, String recipientRoutingDetails,
                    OffsetDateTime updatedAt, OffsetDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
        this.fxRate = fxRate;
        this.status = status;
        this.idempotencyKey = idempotencyKey;
        this.recipientRoutingDetails = recipientRoutingDetails;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public void updateStatus(TransferStatus status) {
        this.status = status;
        this.updatedAt = OffsetDateTime.now();
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public String getSourceCurrency() { return sourceCurrency; }
    public String getTargetCurrency() { return targetCurrency; }
    public BigDecimal getSourceAmount() { return sourceAmount; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public BigDecimal getFxRate() { return fxRate; }
    public TransferStatus getStatus() { return status; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public String getRecipientRoutingDetails() { return recipientRoutingDetails; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
}
