package br.com.mercado.dto;

import br.com.mercado.model.entity.Admin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class AdminDTO {

    @NotBlank
    private String login;

    @NotBlank
    private String senha;

    public AdminDTO(Admin admin){
        this.login = admin.getLogin();
        this.senha = admin.getSenha();
    }
}