package br.com.mercado.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter@Setter
public class Estoque {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 1;

    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL)
    List<Produto> produtos = new ArrayList<>();

    private Integer quantidadeDoProduto;

    public Estoque(Integer quantidadeDoProduto) {
        this.quantidadeDoProduto = quantidadeDoProduto;
    }


    public void adicionarProduto(Produto produto, Integer quantidade){
        this.getProdutos().add(produto);
        this.quantidadeDoProduto = quantidade;
    }
}