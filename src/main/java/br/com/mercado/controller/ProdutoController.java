package br.com.mercado.controller;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.dto.ClienteDTO;
import br.com.mercado.dto.ProdutoDTO;
import br.com.mercado.dto.ProdutoNewDTO;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    public ResponseEntity<ProdutoDTO> inserir(@Valid @RequestBody ProdutoNewDTO produtoNewDTO) {
        log.info("Iniciando a insercao do produto");

        Produto produto = produtoService.fromDTO(produtoNewDTO);

        produtoService.inserir(produto);

        ProdutoDTO produtoDTO = produtoService.fromEntityDTO(produto);
        log.info("insercao feita com sucesso do produto!");
        return new ResponseEntity<ProdutoDTO>(produtoDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "busca um produto por codigo")
    public ResponseEntity<ProdutoDTO> buscarPorCodigo(@PathVariable Integer id) {
        log.info("Iniciando a busca por codigo");

        Produto produto = produtoService.buscarPorCodigo(id);

        log.info("busca feita com sucesso");

        ProdutoDTO produtoDTO = produtoService.fromEntityDTO(produto);

        return ResponseEntity.ok().body(produtoDTO);
    }

    @GetMapping("/codigoBarras/{codBarras}")
    @ApiOperation(value = "busca um produto por codigo de barras")
    public ResponseEntity<ProdutoDTO> buscarPorCodBarras(@PathVariable String codBarras) {
        log.info("Iniciando a busca por codigo");

        Produto produto = produtoService.buscarPorCodBarras(codBarras);

        log.info("busca feita com sucesso");

        ProdutoDTO produtoDTO = produtoService.fromEntityDTO(produto);

        return ResponseEntity.ok().body(produtoDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        log.info("iniciando a listagem de todos os produtos");

        List<Produto> produtos = produtoService.listarTodos();

        List<ProdutoDTO> produtosDTO = produtos.stream().map(obj -> new ProdutoDTO(obj)).collect(Collectors.toList());


        return new ResponseEntity<List<ProdutoDTO>>(produtosDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "alterar produto")
    public ResponseEntity<ProdutoDTO> alterar(@Valid @RequestBody ProdutoDTO produtoDTO, @PathVariable Integer id) {

        log.info("Iniciando alteracao de produto");

        Produto produto = produtoService.fromDTO(produtoDTO);
        produto.setId(id);
        produto = produtoService.alterar(produto);

        produtoDTO = produtoService.fromEntityDTO(produto);

        log.info("Produto alterada com sucesso!");
        return new ResponseEntity<ProdutoDTO>(produtoDTO, HttpStatus.OK);
    }

    @GetMapping("/page")
    @ApiOperation(value = "obter Paginacao de produtos")
    public ResponseEntity<Page<ProdutoDTO>> obterPagina(
            @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "24") Integer linhasPorPage,
            @RequestParam(defaultValue = "nome") String ordenarPor, @RequestParam(defaultValue = "ASC") String direcao) {
        log.info(" Iniciando a busca por paginacao de produtos");
        Page<Produto> lista = produtoService.obterPagina(page, linhasPorPage, ordenarPor, direcao);

        Page<ProdutoDTO> listDTO = lista.map(obj -> new ProdutoDTO(obj));
        //nao mostrar os produtos da categoria no postman
        log.info("Busca feita com sucesso e retornando as paginas");
        return ResponseEntity.ok().body(listDTO);
    }

}
