package payment_processing_engine.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;
import payment_processing_engine.domain.model.LedgerEntry;
import payment_processing_engine.domain.repository.LedgerRepository;
import payment_processing_engine.infrastructure.persistence.repository.SpringDataLedgerRepository;

@Component
public class LedgerPersistenceAdapter implements LedgerRepository {

    private final SpringDataLedgerRepository jpaLedgerRepository;

    public LedgerPersistenceAdapter(SpringDataLedgerRepository jpaLedgerRepository) {
        this.jpaLedgerRepository = jpaLedgerRepository;
    }

    @Override
    public void save(LedgerEntry entry) {

    }
}