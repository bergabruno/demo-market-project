package br.com.mercado.service.impl;

import br.com.mercado.dto.AdminNewDTO;
import br.com.mercado.model.entity.Admin;
import br.com.mercado.model.entity.Admin;
import br.com.mercado.model.entity.AdminLogin;
import br.com.mercado.repository.AdminRepository;
import br.com.mercado.service.AdminService;
import br.com.mercado.service.exceptions.DataIntegrityException;
import br.com.mercado.service.exceptions.NegocioException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder pw;

    public Admin inserir(Admin admin) {

        if(adminRepository.existsByLogin(admin.getLogin()))
            throw new DataIntegrityException("Ja existe um Login com esses caracteres");

        adminRepository.save(admin);
        return admin;
    }

    @Override
    public Optional<AdminLogin> logar(Optional<AdminLogin> admin) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Optional<Admin> adminBancoDados = adminRepository.findByLogin(admin.get().getLogin());

        if (adminBancoDados.isPresent()) {
            if (encoder.matches(admin.get().getSenha(), adminBancoDados.get().getSenha())) {
                String auth = admin.get().getLogin() + ":" + admin.get().getSenha();

                byte[] encondedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
                String authHeader = "Basic " + new String(encondedAuth);

                admin.get().setSenha(adminBancoDados.get().getSenha());
                admin.get().setToken(authHeader);
                admin.get().setLogin(adminBancoDados.get().getLogin());
                return admin;
            }
        } else {
            throw new RuntimeException("Usuario não encontrado");
        }
        throw new RuntimeException("Senha Incorreta");

    }

    public Admin fromDTO(AdminNewDTO adminDTO) {
        return new Admin(null, adminDTO.getLogin(), pw.encode(adminDTO.getSenha()));
    }
}
