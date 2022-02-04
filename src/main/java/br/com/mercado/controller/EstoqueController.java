package br.com.mercado.controller;

import br.com.mercado.dto.EstoqueDTO;
import br.com.mercado.model.entity.Estoque;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.model.entity.ProdutoEstoque;
import br.com.mercado.repository.EstoqueRepository;
import br.com.mercado.repository.ProdutoEstoqueRepository;
import br.com.mercado.service.EstoqueService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/estoque")
@Api(value = "Estoque Controller")
public class EstoqueController {

    EstoqueRepository estoqueRepository;

    EstoqueService estoqueService;

    @GetMapping
    public ResponseEntity<EstoqueDTO> obterEstoque(){
            Estoque estoque = estoqueRepository.findById(1).get();

            EstoqueDTO estoqueDTO = new EstoqueDTO(estoque);

        return ResponseEntity.ok(estoqueDTO);
    }

    @PostMapping("/adicionar/{codBarras}")
    public ResponseEntity<Estoque> addProduto(@PathVariable String codBarras,
                                              @RequestParam(defaultValue = "1") int quantidade){
        Estoque estoque = estoqueService.addProdutoEstoque(codBarras, quantidade);

        return ResponseEntity.ok(estoque);
    }
}
