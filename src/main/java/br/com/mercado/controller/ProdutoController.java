package br.com.mercado.controller;

import br.com.mercado.dto.ProdutoNewDTO;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
    public ResponseEntity<ProdutoNewDTO> inserir(@Valid @RequestBody ProdutoNewDTO produtoNewDTO){
        log.info("Iniciando a insercao do produto");

        Produto produto = produtoService.fromDTO(produtoNewDTO);

        produtoService.inserir(produto);

        log.info("insercao feita com sucesso do produto!");
        return new ResponseEntity<ProdutoNewDTO>(produtoNewDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "busca um produto por codigo")
    public ResponseEntity<ProdutoNewDTO> buscarPorCodigo(@PathVariable Integer id){
        log.info("Iniciando a busca por codigo");

        Produto produto = produtoService.buscarPorCodigo(id);

        log.info("busca feita com sucesso");

        ProdutoNewDTO produtoDTO = produtoService.fromEntity(produto);

        return ResponseEntity.ok().body(produtoDTO);
    }


    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos(){
        log.info("iniciando a listagem de todos os produtos");

        List<Produto> produtos = produtoService.listarTodos();

        return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
    }




}
