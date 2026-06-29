package payment_processing_engine.domain.model;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Ledger {
    private final UUID transactionId;
    private final List<LedgerEntry> entries;
    private final Instant timestamp;

    public Ledger(UUID transactionId) {
        this.transactionId = transactionId;
        this.entries = new ArrayList<>();
        this.timestamp = Instant.now();
    }

    public Ledger(UUID transactionId, List<LedgerEntry> entries, Instant timestamp) {
        this.transactionId = transactionId;
        this.entries = new ArrayList<>(entries);
        this.timestamp = timestamp;
    }

    public void recordDoubleEntry(UUID sourceAccountId, UUID targetAccountId, Money amount) {
        // Balanced journal entry: Source debited, Target credited
        entries.add(new LedgerEntry(UUID.randomUUID(), sourceAccountId, amount, LedgerEntry.TransactionType.DEBIT, timestamp));
        entries.add(new LedgerEntry(UUID.randomUUID(), targetAccountId, amount, LedgerEntry.TransactionType.CREDIT, timestamp));
    }

    public UUID getTransactionId() { return transactionId; }
    public List<LedgerEntry> getEntries() { return List.copyOf(entries); }
    public Instant getTimestamp() { return timestamp; }
}