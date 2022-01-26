package br.com.mercado.model.entity;

import br.com.mercado.model.entity.enums.StatusPedido;
import br.com.mercado.model.entity.enums.TipoPagamento;
import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Pedido implements Serializable {
    private final static long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Cliente cliente;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("Data do pedido:")
    private LocalDate dataPedido;

    @JsonIgnore
    private Integer statusPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemPedido> itens = new ArrayList<>();

    @JsonIgnore
    private Integer tipoPagamento;

    private Double valorTotal;

    public Pedido(Integer id, Cliente cliente, StatusPedido statusPedido, LocalDate dataPedido) {
        this.id = id;
        this.cliente = cliente;
        this.statusPedido = (statusPedido == null) ? null : statusPedido.getCod();
        this.dataPedido = dataPedido;
    }

    public StatusPedido getStatusPedido() {
        return StatusPedido.toEnum(statusPedido);
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido.getCod();
    }

    public TipoPagamento getTipoPagamento() {
        return TipoPagamento.toEnum(tipoPagamento);
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento.getCod();
    }

    //JSON mostra esse metodo
    //caso a classe nao tenha o nome da var apos o GET, o JSON executa esse metodo!
    @JsonProperty("Valor total do pedido:")
    public double getValorTotal() {
        double soma = 0.0;
        for (ItemPedido ip : itens) {
            soma = soma + ip.getSubTotal();
        }
        String format = String.format("%.2f", soma);
        format = format.replaceAll(",", ".");

        this.valorTotal = Double.parseDouble(format);

        return valorTotal;
    }

    @JsonProperty("Status do pedido:")
    public String getStatusDoPedido() {
        return StatusPedido.toEnum(statusPedido).getDescricao();
    }

    @JsonProperty("Formato de pagamento:")
    public String getTipoDePagamento() {
        if (tipoPagamento == null) {
            return null;
        }
        return TipoPagamento.toEnum(tipoPagamento).getDescricao();
    }

}
