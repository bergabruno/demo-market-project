package br.com.mercado.dto;

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

}
