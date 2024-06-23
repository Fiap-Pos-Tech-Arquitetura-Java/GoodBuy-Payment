package br.com.fiap.postech.goodbuy.payment.repository;

import br.com.fiap.postech.goodbuy.payment.entity.Item;
import br.com.fiap.postech.goodbuy.payment.entity.Payment;
import br.com.fiap.postech.goodbuy.payment.entity.Summary;
import br.com.fiap.postech.goodbuy.payment.helper.PaymentHelper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
public class PaymentRepositoryIT {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentRepositoryIT(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Test
    void devePermitirCriarEstrutura() {
        var totalRegistros = paymentRepository.count();
        assertThat(totalRegistros).isEqualTo(4);
    }

    @Test
    void devePermitirCadastrarPayment() {
        // Arrange
        var payment = PaymentHelper.getPayment(true);
        // Act
        var paymentCadastrado = paymentRepository.save(payment);
        // Assert
        assertThat(paymentCadastrado).isInstanceOf(Payment.class).isNotNull();
        assertThat(paymentCadastrado.getId()).isEqualTo(payment.getId());
        assertThat(paymentCadastrado.getSummary()).isEqualTo(payment.getSummary());
    }
    @Test
    void devePermitirBuscarPayment() {
        // Arrange
        var id = UUID.fromString("e1eb1653-85f5-4981-bc6c-2a44e0e00446");
        var summary = new Summary(UUID.fromString("20aacc59-4637-41a3-8f5a-060625dd4e6c"), null);
        var item = new Item();
        item.setId(UUID.fromString("1ace24dc-be80-4944-9a82-4d453ab31999"));
        var method = "PIX";
        var payment = new Payment();
        payment.setId(id);
        // Act
        var paymentOpcional = paymentRepository.findById(id);
        // Assert
        assertThat(paymentOpcional).isPresent();
        paymentOpcional.ifPresent(
                paymentRecebido -> {
                    assertThat(paymentRecebido).isInstanceOf(Payment.class).isNotNull();
                    assertThat(paymentRecebido).isEqualTo(payment);
                    assertThat(paymentRecebido.getId()).isEqualTo(id);
                    assertThat(paymentRecebido.getSummary()).isEqualTo(summary);
                    assertThat(paymentRecebido.getSummary().getId()).isEqualTo(summary.getId());
                    assertThat(paymentRecebido.getSummary().getItens().get(0)).isEqualTo(item);
                    assertThat(paymentRecebido.getMethod()).isEqualTo(method);
                }
        );
    }
    @Test
    void devePermitirRemoverPayment() {
        // Arrange
        var id = UUID.fromString("6264e223-961e-4fa9-9cfd-69aef3eed8c7");
        // Act
        paymentRepository.findById(id).orElseThrow();
        paymentRepository.deleteById(id);
        // Assert
        var paymentOpcional = paymentRepository.findById(id);
        assertThat(paymentOpcional).isEmpty();
    }
    @Test
    void devePermitirListarPayments() {
        // Arrange
        // Act
        var paymentsListados = paymentRepository.findAll();
        // Assert
        assertThat(paymentsListados).hasSize(4);
    }
}
