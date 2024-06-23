package br.com.fiap.postech.goodbuy.payment.service;

import br.com.fiap.postech.goodbuy.payment.helper.PaymentHelper;
import br.com.fiap.postech.goodbuy.payment.integration.ExternalPaymentMethodIntegration;
import br.com.fiap.postech.goodbuy.payment.integration.ShopCartIntegration;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
public class PaymentServiceIT {

    @Autowired
    private PaymentService paymentService;

    @MockBean
    ShopCartIntegration shopCartIntegration;

    @MockBean
    ExternalPaymentMethodIntegration externalPaymentMethodIntegration;

    @Test
    void devePermitirAccomplishPayment() {
        // Arrange
        var paymentRequest = PaymentHelper.getPayment(false);
        var summary = PaymentHelper.getSummary();
        when(shopCartIntegration.getSummary(anyString())).thenReturn(summary);
        // Act
        paymentService.accomplish(anyString(), paymentRequest);
        // Assert
        assertThat(paymentRequest.getId()).isNotNull();
    }
}
