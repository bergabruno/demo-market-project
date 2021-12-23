package br.com.mercado.controller;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.service.CategoriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/categorias")
@Api(value = "Categoria Controller")
public class CategoriaController {

    CategoriaService categoriaService;

    private final Logger log = Logger.getLogger("br.com.mercado.controller.CategoriaController");

    @PostMapping
    @ApiOperation(value = "Inserir categoria")
    public ResponseEntity<Void> inserir(@Valid @RequestBody CategoriaDTO categoriaDTO){
        log.info("Iniciando insercao de categoria");

        Categoria categoria = categoriaService.fromDTO(categoriaDTO);
        categoria = categoriaService.inserir(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/buscar/{id}").buildAndExpand(categoria.getId()).toUri();

        log.info("Categoria criada com sucesso retornando o codigo da url para resgatar ela.");
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Alterar categoria")
    public ResponseEntity<Void> alterar(@Valid @RequestBody CategoriaDTO categoriaDTO,
                                        @PathVariable Integer id){
        log.info("Iniciando alteracao de categoria");
        Categoria categoria = categoriaService.fromDTO(categoriaDTO);
        categoria.setId(id);
        categoria = categoriaService.alterar(categoria);

        log.info("Categoria alterada com sucesso!");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "deletar categoria")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        log.info("Iniciando delecao de categoria");
        categoriaService.deletar(id);
        log.info("delecao feita com sucesso!");
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation(value = "Listar todas categorias")
    public ResponseEntity<List<CategoriaDTO>> listarTodos(){
        log.info("Iniciando listagem de todas categorias");

        List<Categoria> lista = categoriaService.obterTodos();

        //nao mostrar os produtos da categoria no postman
        List<CategoriaDTO> listDTO = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());

        log.info("Listagem feita com sucesso e retornando no corpo");
        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar categoria")
    public ResponseEntity<Categoria> buscar(@PathVariable Integer id){
        log.info("Iniciando busca de categoria");
        Categoria categoria = categoriaService.buscar(id);

        log.info("busca feita com sucesso");
        return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
    }

    @GetMapping("/page")
    @ApiOperation(value = "Obter Paginacao de categoria")
    public ResponseEntity<Page<CategoriaDTO>> obterPagina(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "24") Integer linhasPorPage,
            @RequestParam(defaultValue = "nome") String ordenarPor, @RequestParam(defaultValue = "ASC") String direcao){
        log.info(" Iniciando a busca por paginacao de categoria");
        Page<Categoria> lista = categoriaService.obterPagina(page, linhasPorPage, ordenarPor, direcao);

        Page<CategoriaDTO> listDTO = lista.map(obj -> new CategoriaDTO(obj));
        //nao mostrar os produtos da categoria no postman
        log.info("Busca feita com sucesso e retornando as paginas");
        return ResponseEntity.ok().body(listDTO);
    }
}
