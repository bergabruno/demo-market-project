package br.com.mercado.model.entity;

import br.com.mercado.dto.AdminDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminLogin {

    private String login;

    private String senha;

    private String token;

    public AdminLogin(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public AdminLogin(AdminDTO adminDTO) {
        this.login = adminDTO.getLogin();
        this.senha = adminDTO.getSenha();
    }
}
