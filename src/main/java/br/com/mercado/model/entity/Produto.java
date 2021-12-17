package br.com.mercado.model.entity;

import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter@Setter
public class Produto implements Serializable {
    private final static long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String codBarras;

    private String dataValidade;

    private Integer quantidade;

    private Double valorUnitario;

    @ManyToMany(cascade= CascadeType.ALL)
    @JoinTable(name="produto_pedido",
    joinColumns = @JoinColumn(name = "produto_id"), inverseJoinColumns = @JoinColumn(name = "pedido_id"))
    List<Pedido> pedidos = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PRODUTO_CATEGORIA",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;


    public Produto(Integer id, String nome, String codBarras, String dataValidade, Integer quantidade, Double valorUnitario) {
        this.id = id;
        this.nome = nome;
        this.codBarras = codBarras;
        this.dataValidade = dataValidade;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public void setCategoria(Categoria categoria){
        if(categorias == null){
            categorias = new ArrayList<>();
        }
        categorias.add(categoria);
    }
}
