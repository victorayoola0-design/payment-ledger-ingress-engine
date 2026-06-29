package payment_processing_engine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import payment_processing_engine.domain.repository.AccountRepository;
import payment_processing_engine.domain.repository.LedgerRepository;
import payment_processing_engine.domain.repository.TransferRepository;
import payment_processing_engine.domain.service.PaymentTransferService;

@Configuration
public class DomainConfig {

    @Bean
    public PaymentTransferService paymentTransferService(AccountRepository accountRepository, TransferRepository transferRepository,
    LedgerRepository ledgerRepository){
        return new PaymentTransferService(accountRepository, transferRepository, ledgerRepository);
    }
}
