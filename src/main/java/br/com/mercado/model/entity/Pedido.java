package br.com.mercado.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter@Setter
public class Pedido implements Serializable {
    private final static long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(mappedBy = "pedidos", cascade = CascadeType.ALL)
    private List<Produto> produtos = new ArrayList<>();

    @ManyToOne
    @JsonManagedReference
    private Cliente cliente;

    private Double valorTotal;

    public Pedido(Integer id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
    }

    public Double getValorTotal(){
        Double valor = 0.0;
        for (Produto p : getProdutos()) {
            valor += p.getValorUnitario();
        }
        return valor;
    }

}
