package br.com.mercado.service;

import br.com.mercado.dto.AdminNewDTO;
import br.com.mercado.model.entity.Admin;
import br.com.mercado.model.entity.AdminLogin;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface AdminService {

    public Admin inserir(Admin admin);

    public Admin fromDTO(AdminNewDTO adminDTO);

    public AdminLogin logar(AdminLogin admin);

}
