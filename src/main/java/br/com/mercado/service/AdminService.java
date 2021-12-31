package br.com.mercado.service;

import br.com.mercado.dto.AdminNewDTO;
import br.com.mercado.model.entity.Admin;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AdminService {

    public Admin inserir(Admin admin);

    public Admin fromDTO(AdminNewDTO adminDTO);

    public Optional<Admin> logar(Optional<Admin> admin);


}
