package payment_processing_engine.domain.repository;

import org.springframework.stereotype.Repository;
import payment_processing_engine.domain.model.Transfer;

import java.util.Optional;


public interface TransferRepository {
    Optional<Transfer> findByIdempotencyKey(String idempotencyKey);
    void save(Transfer transfer);
}
