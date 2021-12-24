package br.com.mercado.model.entity.enums;

import br.com.mercado.model.entity.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.OneToOne;

@AllArgsConstructor
@Getter
public enum StatusPedido {

    FINALIZADO(1, "Finalizado"),
    CANCELADO(2, "Cancelado"),
    EM_ANDAMENTO(3, "Em andamento");

    private int cod;
    private String descricao;


    public static StatusPedido toEnum(Integer cod) {
        if (cod == null)
            return null;

        for (StatusPedido x : StatusPedido.values()) {
            if (cod.equals(x.getCod()))
                return x;
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }}
