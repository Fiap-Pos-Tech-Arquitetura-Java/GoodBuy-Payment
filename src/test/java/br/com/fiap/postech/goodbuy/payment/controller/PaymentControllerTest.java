package br.com.fiap.postech.goodbuy.payment.controller;

import br.com.fiap.postech.goodbuy.payment.entity.Payment;
import br.com.fiap.postech.goodbuy.payment.helper.PaymentHelper;
import br.com.fiap.postech.goodbuy.payment.security.SecurityHelper;
import br.com.fiap.postech.goodbuy.payment.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerTest {
    public static final String PAYMENT = "/payment";

    private MockMvc mockMvc;
    @Mock
    private PaymentService paymentService;
    @Mock
    private SecurityHelper securityHelper;

    private AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        PaymentController shopCartController = new PaymentController(paymentService, securityHelper);
        mockMvc = MockMvcBuilders.standaloneSetup(shopCartController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    public static String asJsonString(final Object object) throws Exception {
        return new ObjectMapper().writeValueAsString(object);
    }

    @Test
    void devePermitirObterSumario() throws Exception {
        // Arrange
        var summary = PaymentHelper.getSummary();
        when(paymentService.getSummary(anyString())).thenReturn(summary);
        when(securityHelper.getToken()).thenReturn("anyString()");
        // Act
        mockMvc.perform(get(PAYMENT)).andExpect(status().isOk());
        // Assert
        verify(paymentService, times(1)).getSummary(anyString());
        verify(securityHelper, times(1)).getToken();
    }

    @Test
    void devePermitirAccomplishPayment() throws Exception {
        // Arrange
        var payment = PaymentHelper.getPayment(false);
        when(paymentService.accomplish(anyString(), any(Payment.class))).thenReturn(payment);
        when(securityHelper.getToken()).thenReturn("anyString()");
        // Act
        mockMvc.perform(
                        post(PAYMENT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(payment)))
                .andExpect(status().isOk());
        // Assert
        verify(paymentService, times(1)).accomplish(anyString(), any(Payment.class));
        verify(securityHelper, times(1)).getToken();
    }
}
