package payment_processing_engine.domain.repository;

import payment_processing_engine.domain.model.LedgerEntry;

public interface LedgerRepository {
    void save(LedgerEntry entry);
}
