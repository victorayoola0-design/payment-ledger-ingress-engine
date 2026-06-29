package payment_processing_engine.infrastructure.persistence.entity;

import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "ledger_entries")
public class LedgerEntryJpaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;


}