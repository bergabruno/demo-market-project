package br.com.mercado.service.impl;

import br.com.mercado.dto.AdminNewDTO;
import br.com.mercado.model.entity.Admin;
import br.com.mercado.model.entity.Admin;
import br.com.mercado.model.entity.AdminLogin;
import br.com.mercado.repository.AdminRepository;
import br.com.mercado.security.AdminDetailsImpl;
import br.com.mercado.security.JWTUtil;
import br.com.mercado.service.AdminService;
import br.com.mercado.service.exceptions.DataIntegrityException;
import br.com.mercado.service.exceptions.NegocioException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private JWTUtil jwtUtil;


    public Admin inserir(Admin admin) {

        if(adminRepository.existsByLogin(admin.getLogin()))
            throw new DataIntegrityException("Ja existe um Login com esses caracteres");

        adminRepository.save(admin);
        return admin;
    }

    @Override
    public AdminLogin logar(AdminLogin admin) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Optional<Admin> adminBancoDados = adminRepository.findByLogin(admin.getLogin());

        if (adminBancoDados.isPresent()) {
            if (encoder.matches(admin.getSenha(), adminBancoDados.get().getSenha())) {
                String token =  "Bearer " + jwtUtil.generateToken(admin.getLogin());
                admin.setToken(token);
                admin.setSenha(pw.encode(admin.getSenha()));
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

    public static AdminDetailsImpl authenticated(){
        try {
            return (AdminDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return null;
        }
    }
}
