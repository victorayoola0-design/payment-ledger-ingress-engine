package payment_processing_engine.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;
import payment_processing_engine.domain.model.Transfer;
import payment_processing_engine.domain.repository.TransferRepository;
import payment_processing_engine.infrastructure.persistence.repository.SpringDataAccountRepository;

import java.util.Optional;


@Component
public class TransferPersistenceAdapter implements TransferRepository {

    private final SpringDataAccountRepository jpaRepository;

    public TransferPersistenceAdapter(SpringDataAccountRepository jpaRepository){
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Transfer> findByIdempotencyKey(String idempotencyKey) {
        return Optional.empty();
    }

    @Override
    public void save(Transfer transfer) {

    }
}
