package payment_processing_engine.domain.service;

import payment_processing_engine.domain.exception.DomainException;
import payment_processing_engine.domain.model.Account;
import payment_processing_engine.domain.model.LedgerEntry;
import payment_processing_engine.domain.model.Money;
import payment_processing_engine.domain.model.Transfer;
import payment_processing_engine.domain.model.TransferStatus;
import payment_processing_engine.domain.repository.AccountRepository;
import payment_processing_engine.domain.repository.LedgerRepository;
import payment_processing_engine.domain.repository.TransferRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public class PaymentTransferService {

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;
    private final LedgerRepository ledgerRepository;

    public PaymentTransferService(AccountRepository accountRepository,
                                  TransferRepository transferRepository,
                                  LedgerRepository ledgerRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
        this.ledgerRepository = ledgerRepository;
    }

    public Transfer executeTransfer(UUID userId, UUID sourceAccountId, UUID targetAccountId,
                                    BigDecimal amount, String sourceCurrency, String targetCurrency,
                                    BigDecimal fxRate, String idempotencyKey, String routingDetails) {

        Optional<Transfer> existingTransfer = transferRepository.findByIdempotencyKey(idempotencyKey);
        if (existingTransfer.isPresent()) {
            return existingTransfer.get();
        }

        BigDecimal targetAmount = amount.multiply(fxRate);

        Transfer transfer = new Transfer(
                UUID.randomUUID(), userId, sourceCurrency, targetCurrency,
                amount, targetAmount, fxRate, TransferStatus.PENDING,
                idempotencyKey, routingDetails, OffsetDateTime.now(), OffsetDateTime.now()
        );
        transferRepository.save(transfer);

        try {
            Account sourceAccount = accountRepository.findByIdForUpdate(sourceAccountId)
                    .orElseThrow(() -> new DomainException("Source account not found."));

            Account targetAccount = accountRepository.findByIdForUpdate(targetAccountId)
                    .orElseThrow(() -> new DomainException("Target account not found."));

            if (!sourceAccount.getCurrency().equals(sourceCurrency) || !targetAccount.getCurrency().equals(targetCurrency)) {
                throw new DomainException("Currency mismatch across account accounts.");
            }

            sourceAccount.debit(amount);
            targetAccount.credit(targetAmount);

            accountRepository.save(sourceAccount);
            accountRepository.save(targetAccount);

            Money sourceMoney = Money.of(amount.toString(), sourceCurrency);
            Money targetMoney = Money.of(targetAmount.toString(), targetCurrency);

            LedgerEntry debitEntry = new LedgerEntry(
                    UUID.randomUUID(),
                    sourceAccount.getId(),
                    sourceMoney,
                    LedgerEntry.TransactionType.DEBIT,
                    Instant.now()
            );

            LedgerEntry creditEntry = new LedgerEntry(
                    UUID.randomUUID(),
                    targetAccount.getId(),
                    targetMoney,
                    LedgerEntry.TransactionType.CREDIT,
                    Instant.now()
            );

            ledgerRepository.save(debitEntry);
            ledgerRepository.save(creditEntry);

            transfer.updateStatus(TransferStatus.SUCCESSFUL);
            transferRepository.save(transfer);

        } catch (Exception e) {
            transfer.updateStatus(TransferStatus.FAILED);
            transferRepository.save(transfer);
            throw new DomainException("Transaction failed: " + e.getMessage());
        }
        return transfer;
    }
}