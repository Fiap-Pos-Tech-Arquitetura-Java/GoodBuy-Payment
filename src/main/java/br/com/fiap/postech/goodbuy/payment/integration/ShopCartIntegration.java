package br.com.fiap.postech.goodbuy.payment.integration;

import br.com.fiap.postech.goodbuy.payment.entity.Summary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ShopCartIntegration {

    @Value("${REMOTE_BASE_URI:http://localhost:8082/goodbuy/shop-cart}")
    String baseURI;

    public Summary getSummary(String token) {
        RestClient restClient = RestClient.create();
        return restClient.get()
                .uri(baseURI)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(Summary.class);
    }

    public Summary deleteShopCart(String token) {
        RestClient restClient = RestClient.create();
        return restClient.delete()
                .uri(baseURI)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(Summary.class);
    }
}
