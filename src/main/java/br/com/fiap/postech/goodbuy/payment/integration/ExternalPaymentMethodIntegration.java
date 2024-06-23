package br.com.fiap.postech.goodbuy.payment.integration;

import br.com.fiap.postech.goodbuy.payment.entity.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExternalPaymentMethodIntegration {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ExternalPaymentMethodIntegration.class);

    public void process(Payment payment) {
        //Envia para um end point mockado lista de itens ,custo total e o tipo de pagamento(Cartao, Boleto,Debito,Pix).
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String paymentAsJson = ow.writeValueAsString(payment);
            LOGGER.info("Pagamento enviado para processamento " + paymentAsJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
