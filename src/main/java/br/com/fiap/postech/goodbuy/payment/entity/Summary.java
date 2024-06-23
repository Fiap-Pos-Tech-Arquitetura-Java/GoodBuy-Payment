package br.com.fiap.postech.goodbuy.payment.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_summary")
public class Summary {
    @Id UUID id;
    @OneToMany(cascade = CascadeType.ALL)

    @JoinColumn(name = "id_summary", nullable = false)
    List<Item> itens;

    public Summary() {
        super();
    }

    public Summary(UUID id, List<Item> itens) {
        this();
        this.id = id;
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Summary summary)) return false;
        return Objects.equals(id, summary.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public UUID getId() {
        return id;
    }

    public List<Item> getItens() {
        return itens;
    }

    public Double getCustoTotal() {
        if (itens == null) {
            return 0D;
        }
        return itens.stream().map(Item::getValorTotal).reduce(0D, Double::sum);
    }
}
