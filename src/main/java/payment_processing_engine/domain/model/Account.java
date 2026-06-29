package payment_processing_engine.domain.model;

import lombok.Data;
import org.springframework.stereotype.Repository;
import payment_processing_engine.domain.exception.DomainException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Account {
        private final UUID id;
        private final UUID userId;
        private final String currency;
        private BigDecimal balance;
        private Long version;
        private final OffsetDateTime createdAt;

        public Account(UUID id, UUID userId, String currency, BigDecimal balance, Long version, OffsetDateTime createdAt) {
                this.id = id;
                this.userId = userId;
                this.currency = currency;
                this.balance = balance;
                this.version = version;
                this.createdAt = createdAt;
        }

        public void debit(BigDecimal amount) {
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new DomainException("Debit amount must be positive.");
                }
                if (this.balance.compareTo(amount) < 0) {
                        throw new DomainException("Insufficient balance for account ID: " + id);
                }
                this.balance = this.balance.subtract(amount);
        }

        public void credit(BigDecimal amount) {
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new DomainException("Credit amount must be positive.");
                }
                this.balance = this.balance.add(amount);
        }

        public UUID getId() { return id; }
        public UUID getUserId() { return userId; }
        public String getCurrency() { return currency; }
        public BigDecimal getBalance() { return balance; }
        public Long getVersion() { return version; }
        public OffsetDateTime getCreatedAt() { return createdAt; }
}