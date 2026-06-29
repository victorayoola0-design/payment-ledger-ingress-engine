package payment_processing_engine.infrastructure.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class AccountJpaEntity {
        @Id
        private UUID id;

        @Column(name = "user_id", nullable = false)
        private UUID userId;

        @Column(nullable = false, length = 3)
        private String currency;

        @Column(nullable = false, precision = 18, scale = 4)
        private BigDecimal balance;

        @Version
        @Column(nullable = false)
        private Long version; // Optimistic locking mechanism

        @Column(name = "created_at", updatable = false)
        private OffsetDateTime createdAt;
}
