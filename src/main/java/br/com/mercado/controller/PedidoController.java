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

    @GetMapping("/buscar/{id}")
//    @ApiOperation(value = "busca por codigo")
    public ResponseEntity<Pedido> buscarPorCodigo(@PathVariable Integer id){
        Pedido pedido = pedidoService.buscar(id);

        if(pedido == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(pedido);
    }
}
