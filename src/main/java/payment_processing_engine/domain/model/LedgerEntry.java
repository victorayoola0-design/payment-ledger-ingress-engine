package payment_processing_engine.domain.model;

import java.time.Instant;
import java.util.UUID;

public record LedgerEntry(
        UUID entryId,
        UUID accountId,
        Money amount,
        TransactionType type,
        Instant timestamp
) {
    public enum TransactionType { CREDIT, DEBIT }
}
