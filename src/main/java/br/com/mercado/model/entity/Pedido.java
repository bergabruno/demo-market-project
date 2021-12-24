package br.com.mercado.model.entity;

import br.com.mercado.model.entity.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany (mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    @ManyToOne
    private Cliente cliente;

    private Integer statusPedido;

    //criar status do pedido
    //Pago - Cancelado - aberto

//    private Pagamento pagamento;

    public Pedido(Integer id, Cliente cliente, StatusPedido statusPedido ) {
        this.id = id;
        this.cliente = cliente;
        this.statusPedido = (statusPedido==null) ? null : statusPedido.getCod();
    }

    public StatusPedido getStatusPedido() {
        return StatusPedido.toEnum(statusPedido);
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido.getCod();
    }



    //JSON mostra esse metodo
    //caso a classe nao tenha o nome da var apos o GET, o JSON executa esse metodo!
    public double getValorTotal() {
        double soma = 0.0;
        for (ItemPedido ip : itens) {
            soma = soma + ip.getSubTotal();
        }
        return soma;
    }

}
