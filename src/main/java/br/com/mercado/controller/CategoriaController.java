package br.com.mercado.controller;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/categorias")
@Api(value = "Categoria Controller", description = "REST API for Pet")
public class CategoriaController {

    CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Void> inserir(@Valid @RequestBody CategoriaDTO categoriaDTO){

        Categoria categoria = categoriaService.fromDTO(categoriaDTO);
        categoria = categoriaService.inserir(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/buscar/{id}").buildAndExpand(categoria.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> alterar(@Valid @RequestBody CategoriaDTO categoriaDTO,
                                        @PathVariable Integer id){
        Categoria categoria = categoriaService.fromDTO(categoriaDTO);
        categoria.setId(id);
        categoria = categoriaService.alterar(categoria);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodos(){
        List<Categoria> lista = categoriaService.obterTodos();

        //nao mostrar os produtos da categoria no postman
        List<CategoriaDTO> listDTO = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar categoria")
    public ResponseEntity<Categoria> buscar(@PathVariable Integer id){
        Categoria categoria = categoriaService.buscar(id);

        return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CategoriaDTO>> obterPagina(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "24") Integer linhasPorPage,
            @RequestParam(defaultValue = "nome") String ordenarPor, @RequestParam(defaultValue = "ASC") String direcao){
        Page<Categoria> lista = categoriaService.obterPagina(page, linhasPorPage, ordenarPor, direcao);

        Page<CategoriaDTO> listDTO = lista.map(obj -> new CategoriaDTO(obj));
        //nao mostrar os produtos da categoria no postman

        return ResponseEntity.ok().body(listDTO);
    }
}
