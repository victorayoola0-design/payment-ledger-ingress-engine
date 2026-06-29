package payment_processing_engine.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;
import payment_processing_engine.domain.model.Account;
import payment_processing_engine.domain.repository.AccountRepository;
import payment_processing_engine.infrastructure.persistence.entity.AccountJpaEntity;
import payment_processing_engine.infrastructure.persistence.repository.SpringDataAccountRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class AccountRepositoryAdapter implements AccountRepository{

        private final SpringDataAccountRepository springDataAccountRepository;

        public AccountRepositoryAdapter(SpringDataAccountRepository springDataAccountRepository) {
            this.springDataAccountRepository = springDataAccountRepository;
        }

        @Override
        public Optional<Account> findByIdForUpdate(UUID id) {
            return springDataAccountRepository.findByIdForUpdate(id)
                    .map(this::mapToDomain);
        }

        @Override
        public void save(Account account) {
            AccountJpaEntity entity = mapToJpa(account);
            springDataAccountRepository.save(entity);
        }

        private Account mapToDomain(AccountJpaEntity entity) {
            return new Account(
                    entity.getId(), entity.getUserId(), entity.getCurrency(),
                    entity.getBalance(), entity.getVersion(), entity.getCreatedAt()
            );
        }

        private AccountJpaEntity mapToJpa(Account domain) {
            AccountJpaEntity entity = new AccountJpaEntity();
            entity.setId(domain.getId());
            entity.setUserId(domain.getUserId());
            entity.setCurrency(domain.getCurrency());
            entity.setBalance(domain.getBalance());
            entity.setVersion(domain.getVersion());
            entity.setCreatedAt(domain.getCreatedAt());
            return entity;
        }
}
