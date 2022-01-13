package br.com.mercado.controller;

import br.com.mercado.dto.ClienteDTO;
import br.com.mercado.dto.ClienteNewDTO;
import br.com.mercado.model.entity.Cliente;
import br.com.mercado.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
@Api(value = "Cliente Controller")
public class ClienteController {

    ClienteService clienteService;

    private final Logger log = Logger.getLogger("br.com.mercado.controller.ClienteController");

    @PostMapping
    @ApiOperation(value = "inserir um cliente")
    public ResponseEntity<ClienteNewDTO> inserir(@Valid @RequestBody ClienteNewDTO clienteDTO){
        log.info("Iniciando insercao de cliente");

        Cliente cliente = clienteService.fromDTO(clienteDTO);
        clienteService.inserir(cliente);
        clienteDTO = clienteService.fromEntity(cliente);

        log.info("Insercao feita com sucesso do Cliente!");
        return new ResponseEntity<ClienteNewDTO>(clienteDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "buscar cliente por codigo")
    public ResponseEntity<Cliente> buscarPorCodigo(@PathVariable Integer id){
        log.info("Iniciando busca de cliente por id");
        Cliente cliente = clienteService.buscarPorCodigo(id);

        log.info("busca realizada com sucesso.");
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Listar todos os clientes", security = @SecurityRequirement(name = "basicAuth"))
    public ResponseEntity<List<ClienteDTO>> listarTodos(){
        log.info("Iniciando listagem de todos cliente");
        List<Cliente> lista = clienteService.obterTodos();

        //nao mostrar os produtos da cliente no postman
        List<ClienteDTO> listDTO = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());

        log.info("Listagem feita com sucesso e mostrando no corpo da requisicao.");
        return ResponseEntity.ok().body(listDTO);
    }

    
    @PutMapping("/{id}")
    @ApiOperation(value = "alterar um cliente pelo id")
    public ResponseEntity<Cliente> alterar(@Valid @RequestBody ClienteDTO clienteDTO,
                                        @PathVariable Integer id){
        log.info("Iniciando alteracao de cliente");

        Cliente cliente = clienteService.fromDTO(clienteDTO);
        cliente.setId(id);
        cliente = clienteService.alterar(cliente);

        log.info("alteracao feita com sucesso");
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "deletar um cliente por codigo")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        log.info("Iniciando delecao de cliente");

        clienteService.deletar(id);
        log.info("delecao feita com sucesso de cliente");
        return ResponseEntity.noContent().build();
    }
}
