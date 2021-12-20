package br.com.mercado.model.entity;

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
    private long itemPedidoID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private Double desconto;

    private Integer quantidade;


//    public Double getValorTotal(){
//        Double valor = 0.0;
//        for (Produto p : getProduto()) {
//            valor += p.getValorUnitario() * p.getQuantidade();
//        }
//        return valor;
//    }


    public ItemPedido(Produto produto, Pedido pedido, Double desconto, Integer quantidade) {
        this.produto = produto;
        this.pedido = pedido;
        this.desconto = desconto;
        this.quantidade = quantidade;
    }
}
