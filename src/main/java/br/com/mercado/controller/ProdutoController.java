package br.com.mercado.controller;

import br.com.mercado.model.entity.Produto;
import br.com.mercado.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    ProdutoService produtoService;

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Produto> buscarPorCodigo(@PathVariable Integer id){
        Produto produto = produtoService.buscar(id);

        if(produto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().body(produto);
    }

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto){
        produtoService.salvar(produto);

        return ResponseEntity.ok().body(produto);
    }
}
