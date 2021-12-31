package br.com.mercado.dto;

import br.com.mercado.model.entity.Admin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class AdminNewDTO {

    private Integer id;

    @NotBlank
    private String login;

    @NotBlank
    private String senha;

    public AdminNewDTO(Admin admin){
        this.id = admin.getId();
        this.login = admin.getLogin();
        this.senha = admin.getSenha();
    }
}
