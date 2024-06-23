package br.com.fiap.postech.goodbuy.payment.controller;

import br.com.fiap.postech.goodbuy.payment.entity.Summary;
import br.com.fiap.postech.goodbuy.payment.entity.Payment;
import br.com.fiap.postech.goodbuy.payment.security.SecurityHelper;
import br.com.fiap.postech.goodbuy.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@Service
public class PaymentController {

    private final PaymentService paymentService;

    private final SecurityHelper securityHelper;

    @Autowired
    public PaymentController(PaymentService paymentService, SecurityHelper securityHelper) {
        this.paymentService = paymentService;
        this.securityHelper = securityHelper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("!hasRole('ADMIN')")
    @Operation(summary = "sumario do pagamento")
    public Summary getSummary() {
        return paymentService.getSummary(securityHelper.getToken());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("!hasRole('ADMIN')")
    @Operation(summary = "realiza o pagamento dos itens associados a um carrinho de compras.")
    public Payment removeItem(@Valid @RequestBody Payment payment) {
        return paymentService.accomplish(securityHelper.getToken(), payment);
    }
}
