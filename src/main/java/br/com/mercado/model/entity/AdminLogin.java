package br.com.mercado.model.entity;

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
}
