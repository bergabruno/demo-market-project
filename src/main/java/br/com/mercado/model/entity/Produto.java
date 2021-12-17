package br.com.mercado.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Produto implements Serializable {
    private final static long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @ManyToMany(cascade= CascadeType.ALL)
    @JoinTable(name="produto_pedido",
    joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "pedido_id"))
    List<Pedido> pedidos = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PRODUTO_CATEGORIA",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>();


    public Produto(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
