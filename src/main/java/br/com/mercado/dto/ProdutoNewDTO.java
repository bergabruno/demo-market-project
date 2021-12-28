package br.com.mercado.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ProdutoNewDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String codBarras;

    private String dataValidade;

    @NotNull
    private Double valorUnitario;

    @NotNull
    private Integer idCategoria;
}
