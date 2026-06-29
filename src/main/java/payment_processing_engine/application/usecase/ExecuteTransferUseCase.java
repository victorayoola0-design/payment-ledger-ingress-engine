package payment_processing_engine.application.usecase;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import payment_processing_engine.domain.model.Transfer;
import payment_processing_engine.domain.service.PaymentTransferService;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ExecuteTransferUseCase {
    private final PaymentTransferService paymentTransferService;

        public ExecuteTransferUseCase(PaymentTransferService paymentTransferService) {
            this.paymentTransferService = paymentTransferService;
        }

        @Transactional
        public Transfer execute(UUID userId, UUID srcId, UUID destId, BigDecimal amt,
                                String srcCurr, String destCurr, BigDecimal rate,
                                String idempotencyKey, String routingDetails) {

            return paymentTransferService.executeTransfer(
                    userId, srcId, destId, amt, srcCurr, destCurr, rate, idempotencyKey, routingDetails
        );
    }
}
