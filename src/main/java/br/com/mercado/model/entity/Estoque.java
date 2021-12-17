package br.com.mercado.model.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL)
    List<Produto> produtos = new ArrayList<>();

    public Estoque(int id){
        this.id = id;
    }
}
