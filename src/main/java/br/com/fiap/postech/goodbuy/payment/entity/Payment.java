package br.com.fiap.postech.goodbuy.payment.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_payment")
public class Payment {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "method", nullable = false)
    private String method;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_summary", referencedColumnName = "id", nullable = false)
    private Summary summary;

    public Payment() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }
}
