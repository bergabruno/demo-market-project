package br.com.mercado.controller;

import br.com.mercado.model.entity.Produto;
import br.com.mercado.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/produtos")
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Api(value = "Controlador do Produto")
public class ProdutoController {

    ProdutoService produtoService;

    private final Logger log = Logger.getLogger("br.com.mercado.controller.ProdutoController");

    @PostMapping
    @ApiOperation(value = "insere um produto no banco")
    public ResponseEntity<Produto> inserir(@RequestBody Produto produto){
        log.info("Iniciando a insercao do produto");

        produtoService.inserir(produto);

        log.info("insercao feita com sucesso do produto!");
        return ResponseEntity.ok().body(produto);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "busca um produto por codigo")
    public ResponseEntity<Produto> buscarPorCodigo(@PathVariable Integer id){

        log.info("Iniciando a busca por codigo");

        Produto produto = produtoService.buscarPorCodigo(id);

        log.info("busca feita com sucesso");
        return ResponseEntity.ok().body(produto);
    }


}
