package br.com.fiap.postech.goodbuy.payment.repository;

import br.com.fiap.postech.goodbuy.payment.entity.Payment;
import br.com.fiap.postech.goodbuy.payment.helper.PaymentHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentRepositoryTest {
    @Mock
    private PaymentRepository paymentRepository;

    AutoCloseable openMocks;
    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirCadastrarPayment() {
        // Arrange
        var payment = PaymentHelper.getPayment(false);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        // Act
        var savedPayment = paymentRepository.save(payment);
        // Assert
        assertThat(savedPayment).isNotNull().isEqualTo(payment);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void devePermitirBuscarPayment() {
        // Arrange
        var payment = PaymentHelper.getPayment(true);
        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.of(payment));
        // Act
        var paymentOpcional = paymentRepository.findById(payment.getId());
        // Assert
        assertThat(paymentOpcional).isNotNull().containsSame(payment);
        paymentOpcional.ifPresent(
                paymentRecebido -> {
                    assertThat(paymentRecebido).isInstanceOf(Payment.class).isNotNull();
                    assertThat(paymentRecebido.getId()).isEqualTo(payment.getId());
                    assertThat(paymentRecebido.getSummary()).isEqualTo(payment.getSummary());
                    assertThat(paymentRecebido.getMethod()).isEqualTo(payment.getMethod());
                }
        );
        verify(paymentRepository, times(1)).findById(payment.getId());
    }
    @Test
    void devePermitirRemoverPayment() {
        //Arrange
        var id = UUID.randomUUID();
        doNothing().when(paymentRepository).deleteById(id);
        //Act
        paymentRepository.deleteById(id);
        //Assert
        verify(paymentRepository, times(1)).deleteById(id);
    }
    @Test
    void devePermitirListarPayments() {
        // Arrange
        var payment1 = PaymentHelper.getPayment(true);
        var payment2 = PaymentHelper.getPayment(true);
        var listaPayments = Arrays.asList(
                payment1,
                payment2
        );
        when(paymentRepository.findAll()).thenReturn(listaPayments);
        // Act
        var paymentsListados = paymentRepository.findAll();
        assertThat(paymentsListados)
                .hasSize(2)
                .containsExactlyInAnyOrder(payment1, payment2);
        verify(paymentRepository, times(1)).findAll();
    }
}