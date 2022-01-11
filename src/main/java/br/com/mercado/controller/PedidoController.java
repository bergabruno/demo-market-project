package br.com.mercado.controller;

import br.com.mercado.dto.PedidoDTO;
import br.com.mercado.model.entity.Pedido;
import br.com.mercado.service.PedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/pedidos")
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Api(value = "Pedido Controller")
public class PedidoController {

    PedidoService pedidoService;

    private final Logger log = Logger.getLogger("br.com.mercado.controller.PedidoController");

    @PostMapping
    @ApiOperation(value = "criar um pedido")
    public ResponseEntity<PedidoDTO> criarPedido(@Valid @RequestBody PedidoDTO pedidoDTO) {
        log.info("Iniciando a insercao do pedido");

        Pedido pedido = pedidoService.fromDTO(pedidoDTO);
        pedido = pedidoService.criarPedido(pedido);

        pedidoDTO = pedidoService.fromEntity(pedido);

        log.info("Retornando o pedido junto com HttpStatus CREATED");
        return new ResponseEntity<PedidoDTO>(pedidoDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "buscar pedido por codigo")
    public ResponseEntity<Pedido> buscarPorCodigo(@PathVariable Integer id) {
        log.info("Iniciando a busca por codigo do pedido");

        Pedido pedido = pedidoService.buscarPorCodigo(id);

        log.info("busca feita com sucesso e retornando pedido");
        return ResponseEntity.ok().body(pedido);
    }

    @PostMapping("/adicionar-produto/{idPedido}/{codBarras}")
    @ApiOperation(value = "Adicionar produto ao pedido")
    public ResponseEntity<Pedido> addProduto(
            @PathVariable Integer idPedido, @PathVariable String codBarras,
            @RequestParam(defaultValue = "1") int quantidadeProd) {

        log.info("Iniciando a insercao de um produto no pedido");

        Pedido pedido = pedidoService.addProduto(idPedido, codBarras, quantidadeProd);

        log.info("Insercao do produto no pedido com sucesso");

        return new ResponseEntity<Pedido>(pedido, HttpStatus.CREATED);
    }

    @PutMapping("/deletar-produto/{idPedido}/{codBarras}")
    @ApiOperation(value = "deletar um produto do pedido")
    public ResponseEntity<Pedido> delProduto(
            @PathVariable Integer idPedido, @PathVariable String codBarras,
            @RequestParam(defaultValue = "1") int quantidadeProd) {

        pedidoService.delProduto(idPedido, codBarras, quantidadeProd);

        return new ResponseEntity<Pedido>(pedidoService.buscarPorCodigo(idPedido), HttpStatus.CREATED);
    }

    @PutMapping("/finalizar/{id}")
    @ApiOperation(value = "mudar o status do pedido para finalizado")
    public ResponseEntity<Pedido> finalizarPedido(@PathVariable Integer id) {

        log.info("Iniciando a alteracao do status do pedido");

        Pedido pedido = pedidoService.finalizarPedido(id);

        log.info("altercao do status do pedido feita com sucesso!");

        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/cancelar/{id}")
    @ApiOperation(value = "mudar o status do pedido para cancelado")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable Integer id) {
        log.info("Iniciando a alteracao do status do pedido");

        Pedido pedido = pedidoService.cancelarPedido(id);

        log.info("altercao do status do pedido feita com sucesso!");

        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/alterar-pagamento/{id}")
    @ApiOperation(value = "mudar o status do pagamento do pedido")
    public ResponseEntity<Pedido> alterarPagamento(@PathVariable Integer id, @RequestParam(defaultValue = "4") Integer status) {
        log.info("Iniciando a alteracao do status do pagamento do pedido");

        Pedido pedido = pedidoService.tipoPagamento(id,status);

        log.info("altercao do status do pagamento do pedido feita com sucesso!");

        return ResponseEntity.ok(pedido);
    }
}
