package payment_processing_engine.domain.repository;

import org.springframework.stereotype.Repository;
import payment_processing_engine.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository {
    Optional<Account> findByIdForUpdate(UUID id);
    void save(Account account);
}