package br.com.mercado.service;

import br.com.mercado.model.entity.Produto;
import org.springframework.stereotype.Service;

@Service
public interface ProdutoService {

    public Produto buscarPorCodigo(Integer id);

    public void inserir(Produto produto);
}
