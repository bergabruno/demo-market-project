package br.com.mercado.controller;

import br.com.mercado.model.entity.Categoria;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/categorias")
@Api(value = "Categoria Controller", description = "REST API for Pet")
public class CategoriaController {

    CategoriaService categoriaService;


    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar categoria")
    public ResponseEntity<Categoria> buscar(@PathVariable Integer id){
       Categoria categoria = categoriaService.buscar(id);

        return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody Categoria categoria){

        categoria = categoriaService.inserir(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/buscar/{id}").buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@RequestBody Categoria categoria, @PathVariable Integer id){
        categoria.setId(id);
        categoria = categoriaService.alterar(categoria);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
