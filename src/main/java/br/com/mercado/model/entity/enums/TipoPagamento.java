package br.com.mercado.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoPagamento {

    CARTAO_CREDITO(1, "Cartao de Credito"),
    CARTAO_DEBITO(2, "Cartao de Debito"),
    VALE_ALIMENTACAO(3, "Vale Alimentacao"),
    DINHEIRO(4, "Dinheiro");

    private int cod;
    private String descricao;

    public static TipoPagamento toEnum(Integer cod) {
        if (cod == null)
            return null;

        for (TipoPagamento x : TipoPagamento.values()) {
            if (cod.equals(x.getCod()))
                return x;
        }

        throw new IllegalArgumentException("Nao existe nenhum tipo de pagamento com esse id: " + cod);
    }

}
