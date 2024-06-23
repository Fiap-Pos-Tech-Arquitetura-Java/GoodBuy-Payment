package br.com.fiap.postech.goodbuy.payment.service;

import br.com.fiap.postech.goodbuy.payment.entity.Summary;
import br.com.fiap.postech.goodbuy.payment.entity.Payment;

public interface PaymentService {
    Summary getSummary(String token);

    Payment accomplish(String token, Payment paymentRequest);
}
