package br.com.fiap.postech.goodbuy.payment.repository;

import br.com.fiap.postech.goodbuy.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

}
