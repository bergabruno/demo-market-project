package br.com.mercado.security;

import br.com.mercado.model.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class AdminDetailsImpl implements UserDetails {

    private String login;

    private String senha;

    private List<GrantedAuthority> authorities;

    public AdminDetailsImpl(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public AdminDetailsImpl(Admin admin) {
        this.login = admin.getLogin();
        this.senha = admin.getSenha();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
