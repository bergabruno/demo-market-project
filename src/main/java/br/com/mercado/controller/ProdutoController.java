package br.com.mercado.controller;

import br.com.mercado.model.entity.Produto;
import br.com.mercado.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/produtos")
@CrossOrigin(allowedHeaders = "*", origins = "*")
//@Api(value = "Api rest")
public class ProdutoController {

    ProdutoService produtoService;

    @GetMapping("/{id}")
//    @ApiOperation(value = "busca por codigo")
    public ResponseEntity<Produto> buscarPorCodigo(@PathVariable Integer id){
        Produto produto = produtoService.buscar(id);

        return ResponseEntity.ok().body(produto);
    }

    @PostMapping
//    @ApiOperation(value = "salva um produto no banco")
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto){
        produtoService.salvar(produto);

        return ResponseEntity.ok().body(produto);
    }
}
