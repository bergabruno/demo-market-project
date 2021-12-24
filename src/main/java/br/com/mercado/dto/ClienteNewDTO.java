package br.com.mercado.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteNewDTO {

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatorio")
    private String cpf;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Email(message = "email Invalido")
    private String email;

}
