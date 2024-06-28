package br.com.fiap.postech.goodbuy.payment.controller;

import br.com.fiap.postech.goodbuy.payment.helper.PaymentHelper;
import br.com.fiap.postech.goodbuy.payment.helper.UserHelper;
import br.com.fiap.postech.goodbuy.payment.integration.ShopCartIntegration;
import br.com.fiap.postech.goodbuy.security.UserDetailsServiceImpl;
import br.com.fiap.postech.goodbuy.security.enums.UserRole;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class PaymentControllerIT {

    public static final String SHOPCART = "/goodbuy/payment";

    @LocalServerPort
    private int port;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private ShopCartIntegration shopCartIntegration;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void devePermitirObterSumario() {
        var user = UserHelper.getUser(true, "anderson.wagner", UserRole.USER);
        var userDetails = UserHelper.getUserDetails(user);
        var summary = PaymentHelper.getSummary();
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(shopCartIntegration.getSummary(anyString())).thenReturn(summary);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, UserHelper.getToken(user))
                .when()
                .get(SHOPCART)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schemas/summary.schema.json"));
    }
}
