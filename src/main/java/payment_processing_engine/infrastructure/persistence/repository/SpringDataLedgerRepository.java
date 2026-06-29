package payment_processing_engine.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payment_processing_engine.infrastructure.persistence.entity.LedgerEntryJpaEntity;

@Repository
public interface SpringDataLedgerRepository extends JpaRepository<LedgerEntryJpaEntity, Long> {
}
