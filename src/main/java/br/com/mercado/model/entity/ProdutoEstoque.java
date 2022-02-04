package br.com.mercado.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProdutoEstoque {

    @Id
    @Column(name = "produto_estoque_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    //tirar o id
    private Integer produtoEstoqueId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "estoque_id")
    @JsonIgnore
    //nao mostrar o estoque, so preciso do produto.
    private Estoque estoque;

    private Integer quantidade;

    public ProdutoEstoque(Produto produto, Estoque estoque, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.estoque = estoque;
    }
}
