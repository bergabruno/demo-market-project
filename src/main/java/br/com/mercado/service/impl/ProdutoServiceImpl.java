package br.com.mercado.service.impl;

import br.com.mercado.model.entity.Produto;
import br.com.mercado.repository.ProdutoRepository;
import br.com.mercado.service.ProdutoService;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private ProdutoRepository produtoRepository;

    public void inserir(Produto produto){
        if(produto == null)
            throw new RuntimeException("Produto incompleto");
        produtoRepository.save(produto);
    }

    public Produto buscarPorCodigo(Integer id){
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElseThrow(() -> new ObjectNotFoundExcepction("Erro ao encontrar produto com este ID!"));
    }



}
