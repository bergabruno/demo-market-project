package br.com.mercado.dto;

import br.com.mercado.model.entity.Estoque;
import br.com.mercado.model.entity.ProdutoEstoque;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EstoqueDTO {

    private List<ProdutoEstoque> produtos;


    public EstoqueDTO(Estoque estoque){
        this.produtos = estoque.getProdutos();
    }
}
