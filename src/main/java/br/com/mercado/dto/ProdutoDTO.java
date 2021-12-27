package br.com.mercado.dto;

import javax.validation.constraints.NotBlank;

public class ProdutoDTO {

    private Integer id;

    @NotBlank
    private String nome;

    @NotBlank
    private String codBarras;

    private String dataValidade;

    @NotBlank
    private Double valorUnitario;

    private Integer idCategoria;
}
