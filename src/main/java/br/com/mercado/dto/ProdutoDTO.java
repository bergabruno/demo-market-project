package br.com.mercado.dto;

import br.com.mercado.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Integer id;

    @NotBlank
    private String nome;

    @NotBlank
    private String codBarras;

    @NotBlank
    private String dataValidade;

    @NotNull
    private Double valorUnitario;

    private Integer idCategoria;

    public ProdutoDTO(Produto produto){
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.codBarras = produto.getCodBarras();
        this.dataValidade = produto.getDataValidade();
        this.valorUnitario = produto.getValorUnitario();
        this.idCategoria = produto.getCategorias().get(0).getId();
    }

}
