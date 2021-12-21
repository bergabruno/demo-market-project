package br.com.mercado.service;

import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProdutoService {

    private ProdutoRepository produtoRepository;

    public Produto buscar(Integer id){
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElseThrow(() -> new ObjectNotFoundExcepction("Erro ao encontrar produto com este ID!"));
    }

    public void salvar(Produto produto){
        if(produto == null)
            throw new RuntimeException("Produto incompleto");
        produtoRepository.save(produto);
    }
}
