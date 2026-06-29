package payment_processing_engine.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payment_processing_engine.domain.model.Transfer;
import payment_processing_engine.domain.service.PaymentTransferService;
import payment_processing_engine.web.dto.TransferRequest;

@RestController
@RequestMapping("/api/v1/transfers")
public class PaymentTransferController {

    private final PaymentTransferService paymentTransferService;

    public PaymentTransferController(PaymentTransferService paymentTransferService) {
        this.paymentTransferService = paymentTransferService;
    }

    @PostMapping
    public ResponseEntity<Transfer> initiateTransfer(
            @RequestHeader("X-Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody TransferRequest request) {

        Transfer result = paymentTransferService.executeTransfer(
                request.userId(),
                request.sourceAccountId(),
                request.targetAccountId(),
                request.amount(),
                request.sourceCurrency(),
                request.targetCurrency(),
                request.fxRate(),
                idempotencyKey,
                request.routingDetails()
        );
        return ResponseEntity.ok(result);
    }
}