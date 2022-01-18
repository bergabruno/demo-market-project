package br.com.mercado.controller;

import br.com.mercado.security.AdminDetailsImpl;
import br.com.mercado.security.JWTUtil;
import br.com.mercado.service.AdminService;
import br.com.mercado.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        AdminDetailsImpl admin = AdminServiceImpl.authenticated();
        String token = jwtUtil.generateToken(admin.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }
}
