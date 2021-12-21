package br.com.mercado.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ItemPedido {

    @Id
    @Column(name = "item_pedido_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long itemPedidoID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    @JsonIgnore
    private Pedido pedido;

    private Double desconto;

    private Integer quantidade;

    @JsonIgnore
    private Double preco;


    @JsonIgnore
    public double getSubTotal() {
        return (preco - desconto) * quantidade;
    }


    public ItemPedido(Produto produto, Pedido pedido, Double desconto, Integer quantidade, Double preco) {
        this.produto = produto;
        this.pedido = pedido;
        this.desconto = desconto;
        this.preco = preco;
        this.quantidade = quantidade;
    }
}
