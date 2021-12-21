package br.com.mercado.controller;

import br.com.mercado.model.entity.Cliente;
import br.com.mercado.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorCodigo(@PathVariable Integer id){
        Cliente cliente = clienteService.buscar(id);

        if(cliente == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok().body(cliente);
    }


}
