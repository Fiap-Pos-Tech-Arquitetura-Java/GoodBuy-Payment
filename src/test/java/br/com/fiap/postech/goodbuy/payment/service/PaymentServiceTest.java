package br.com.fiap.postech.goodbuy.payment.service;

import br.com.fiap.postech.goodbuy.payment.entity.Summary;
import br.com.fiap.postech.goodbuy.payment.entity.Payment;
import br.com.fiap.postech.goodbuy.payment.helper.PaymentHelper;
import br.com.fiap.postech.goodbuy.payment.integration.ExternalPaymentMethodIntegration;
import br.com.fiap.postech.goodbuy.payment.integration.ShopCartIntegration;
import br.com.fiap.postech.goodbuy.payment.repository.PaymentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {
    private PaymentService paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    ShopCartIntegration shopCartIntegration;

    @Mock
    ExternalPaymentMethodIntegration externalPaymentMethodIntegration;

    private AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        paymentService = new PaymentServiceImpl(paymentRepository, shopCartIntegration, externalPaymentMethodIntegration);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void devePermitirObterSumario() {
        // Arrange
        var quantidade = (long) (Math.random() * 100);
        var summary = PaymentHelper.getSummary(quantidade);
        when(shopCartIntegration.getSummary(anyString())).thenReturn(summary);
        // Act
        var summaryObtido = paymentService.getSummary("anyString()");
        // Assert
        assertThat(summaryObtido).isNotNull();
        assertThat(summaryObtido.getCustoTotal()).isNotNull();
        assertThat(summaryObtido.getCustoTotal()).isEqualTo(2 * quantidade * PaymentHelper.VALOR_UNITARIO);
        assertThat(summaryObtido.getItens()).isNotNull();
        assertThat(summaryObtido.getItens()).isNotEmpty();
        assertThat(summaryObtido.getItens().get(0)).isNotNull();
        assertThat(summaryObtido.getItens().get(0).getValorTotal()).isEqualTo(quantidade * PaymentHelper.VALOR_UNITARIO);
        assertThat(summaryObtido.getItens().get(0)).isNotNull();
        assertThat(summaryObtido.getItens().get(0).getValorTotal()).isEqualTo(quantidade * PaymentHelper.VALOR_UNITARIO);
        verify(shopCartIntegration, times(1)).getSummary(anyString());
    }

    @Test
    void devePermitirObterSumario_emptyShopCart() {
        // Arrange
        var quantidade = (long) (Math.random() * 100);
        var summary = new Summary(UUID.randomUUID(), null);
        when(shopCartIntegration.getSummary(anyString())).thenReturn(summary);
        // Act
        var summaryObtido = paymentService.getSummary("anyString()");
        // Assert
        assertThat(summaryObtido).isNotNull();
        assertThat(summaryObtido.getCustoTotal()).isNotNull();
        assertThat(summaryObtido.getCustoTotal()).isEqualTo(0);
        assertThat(summaryObtido.getItens()).isNull();
        verify(shopCartIntegration, times(1)).getSummary(anyString());
    }

    @Test
    void devePermitirAccomplishPayment() {
        // Arrange
        var paymentRequest = PaymentHelper.getPayment(false);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(r -> r.getArgument(0));
        // Act
        paymentService.accomplish(anyString(), paymentRequest);
        // Assert
        assertThat(paymentRequest.getId()).isNotNull();
    }
}
