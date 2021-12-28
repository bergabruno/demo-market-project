package br.com.mercado.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.DataAmount;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter@Setter
@AllArgsConstructor
public class Produto implements Serializable {
    private final static long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String codBarras;

    private String dataValidade;

    private Double valorUnitario;

    @OneToMany(cascade= CascadeType.ALL, mappedBy = "pedido")
    @JsonIgnore
    List<ItemPedido> itens = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PRODUTO_CATEGORIA",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    @JsonIgnore
    private List<Categoria> categorias = new ArrayList<>();


    public Produto(Integer id, String nome, String codBarras, String dataValidade, Double valorUnitario) {
        this.id = id;
        this.nome = nome;
        this.codBarras = codBarras;
        this.dataValidade = dataValidade;
        this.valorUnitario = valorUnitario;
    }

    public Produto(Integer id, String nome, String codBarras, String dataValidade, Double valorUnitario, List<Categoria> categorias) {
        this.id = id;
        this.nome = nome;
        this.codBarras = codBarras;
        this.dataValidade = dataValidade;
        this.valorUnitario = valorUnitario;
        this.categorias = categorias;
    }

    public void setCategoria(Categoria categoria){
        if(categorias == null){
            categorias = new ArrayList<>();
        }
        categorias.add(categoria);
    }
}
