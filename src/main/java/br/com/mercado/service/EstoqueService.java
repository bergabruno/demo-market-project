package br.com.mercado.service;

import br.com.mercado.model.entity.Estoque;
import br.com.mercado.model.entity.Produto;

public interface EstoqueService {

    public Estoque buscarPorCodigo(Integer id);

    public Estoque addProdutoEstoque(String codBarras, int quantidade);

}
