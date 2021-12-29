package br.com.mercado.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String cpf;

    private String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedido> pedidos= new ArrayList<>();

    public Cliente(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Cliente(Integer id, String nome, String email, String cpf) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }

}
