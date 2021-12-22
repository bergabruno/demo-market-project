package br.com.mercado.controller;

import br.com.mercado.model.entity.Pedido;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/pedidos")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PedidoController {

    PedidoService pedidoService;



    @GetMapping("/{id}")
//    @ApiOperation(value = "busca por codigo")
    public ResponseEntity<Pedido> buscarPorCodigo(@PathVariable Integer id){
        Pedido pedido = pedidoService.buscar(id);

        if(pedido == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(pedido);
    }

    @PostMapping
    public ResponseEntity<Pedido> salvar(@RequestBody Pedido pedido){

        pedidoService.salvar(pedido);

        return new ResponseEntity<Pedido>(pedido, HttpStatus.CREATED);
    }

    @PostMapping("/adicionar-produto/{idPedido}/{codBarras}/{quantidadeProd}")
    public ResponseEntity<Pedido> addProduto(
            @PathVariable Integer idPedido, @PathVariable String codBarras, @PathVariable int quantidadeProd){

        pedidoService.addProduto(idPedido, codBarras, quantidadeProd);

        return new ResponseEntity<Pedido>(pedidoService.buscar(idPedido), HttpStatus.CREATED);

    }
}
