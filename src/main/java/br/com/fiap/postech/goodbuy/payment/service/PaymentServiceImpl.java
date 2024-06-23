package br.com.fiap.postech.goodbuy.payment.service;

import br.com.fiap.postech.goodbuy.payment.entity.Summary;
import br.com.fiap.postech.goodbuy.payment.entity.Payment;
import br.com.fiap.postech.goodbuy.payment.integration.ExternalPaymentMethodIntegration;
import br.com.fiap.postech.goodbuy.payment.integration.ShopCartIntegration;
import br.com.fiap.postech.goodbuy.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final ShopCartIntegration shopCartIntegration;

    private final ExternalPaymentMethodIntegration externalPaymentMethodIntegration;

    @Autowired
    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            ShopCartIntegration shopCartIntegration,
            ExternalPaymentMethodIntegration externalPaymentMethodIntegration) {
        this.paymentRepository = paymentRepository;
        this.shopCartIntegration = shopCartIntegration;
        this.externalPaymentMethodIntegration = externalPaymentMethodIntegration;
    }

    @Override
    public Summary getSummary(String token) {
        return shopCartIntegration.getSummary(token);
    }

    @Override
    public Payment accomplish(String token, Payment payment) {
        payment.setId(UUID.randomUUID());
        payment.setSummary(getSummary(token));
        externalPaymentMethodIntegration.process(payment);
        shopCartIntegration.deleteShopCart(token);
        return paymentRepository.save(payment);
    }
}
