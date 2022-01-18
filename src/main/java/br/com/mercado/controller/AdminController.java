package br.com.mercado.controller;

import br.com.mercado.dto.AdminNewDTO;
import br.com.mercado.model.entity.Admin;
import br.com.mercado.model.entity.AdminLogin;
import br.com.mercado.security.AdminDetailsImpl;
import br.com.mercado.security.JWTUtil;
import br.com.mercado.service.AdminService;
import br.com.mercado.service.impl.AdminServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import java.util.logging.Logger;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/admin")
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Api(value = "Controlador de ADM")
public class AdminController {

    @Autowired
    AdminService adminService;

    private final Logger log = Logger.getLogger("br.com.mercado.controller.AdminController");

    @PostMapping
    @ApiOperation(value = "inserir admin")
    public ResponseEntity<AdminNewDTO> inserir(@Valid @RequestBody AdminNewDTO adminDTO){
        log.info("Iniciando insercao de admin");

        Admin admin = adminService.fromDTO(adminDTO);

        admin = adminService.inserir(admin);

        AdminNewDTO novoAdmin = new AdminNewDTO(admin);

        log.info("Iniciando insercao de admin" + adminDTO.getSenha());
        return new ResponseEntity<AdminNewDTO>(novoAdmin, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ApiOperation(value = "Faz o login do usuário")
    public ResponseEntity<AdminLogin> login(@RequestBody @Valid Optional<AdminLogin> admin) {

        log.info( "Iniciando o login");
        return adminService.logar(admin).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
