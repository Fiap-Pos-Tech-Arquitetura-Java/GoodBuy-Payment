package br.com.fiap.postech.goodbuy.payment.helper;

import br.com.fiap.postech.goodbuy.payment.entity.Item;
import br.com.fiap.postech.goodbuy.payment.entity.Summary;
import br.com.fiap.postech.goodbuy.payment.entity.Payment;

import java.util.List;
import java.util.UUID;

public class PaymentHelper {
    public static final Double VALOR_UNITARIO = 12D;

    public static Summary getSummary() {
        return getSummary(4L);
    }
    public static Summary getSummary(Long quantidade) {
        List<Item> itens = List.of(
                new Item(UUID.randomUUID(),"Old Specked Hein", quantidade, VALOR_UNITARIO),
                new Item(UUID.randomUUID(),"London Pride", quantidade, VALOR_UNITARIO)
        );
        return new Summary(UUID.randomUUID(), itens);
    }

    public static Payment getPayment(Boolean geraId) {
        Payment pix = new Payment();
        pix.setMethod("PIX");
        if (geraId) {
            pix.setId(UUID.randomUUID());
        }
        return pix;
    }
}
