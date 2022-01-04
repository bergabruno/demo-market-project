package br.com.mercado.service;

import br.com.mercado.dto.ProdutoNewDTO;
import br.com.mercado.model.entity.Produto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProdutoService {

    public Produto buscarPorCodigo(Integer id);

    public void inserir(Produto produto);

    public Produto fromDTO(ProdutoNewDTO produtoNewDTO);

    public ProdutoNewDTO fromEntity(Produto produto);

    public List<Produto> listarTodos();
    }
