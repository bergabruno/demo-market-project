package br.com.mercado.controller;

import br.com.mercado.dto.ClienteDTO;
import br.com.mercado.model.entity.Cliente;
import br.com.mercado.service.ClienteService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    ClienteService clienteService;

    @PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@Valid @RequestBody ClienteDTO clienteDTO,
                                        @PathVariable Integer id){
        Cliente cliente = clienteService.fromDTO(clienteDTO);
        cliente.setId(id);
        cliente = clienteService.alterar(cliente);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){ clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos(){
        List<Cliente> lista = clienteService.obterTodos();

        //nao mostrar os produtos da cliente no postman
        List<ClienteDTO> listDTO = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar cliente")
    public ResponseEntity<Cliente> buscar(@PathVariable Integer id){
        Cliente cliente = clienteService.buscar(id);

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }


}
