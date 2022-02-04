package br.com.mercado.service.impl;

import br.com.mercado.model.entity.Estoque;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.model.entity.ProdutoEstoque;
import br.com.mercado.repository.EstoqueRepository;
import br.com.mercado.repository.ProdutoRepository;
import br.com.mercado.service.EstoqueService;
import br.com.mercado.service.ProdutoService;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstoqueServiceImpl implements EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoService produtoService;

    public Estoque buscarPorCodigo(Integer id) {
        Optional<Estoque> cliente = estoqueRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundExcepction("Erro ao encontrar estoque por este codigo!"));
    }

    public Estoque addProdutoEstoque(String codBarras, int quantidade) {
        Produto produtoSalvo = produtoService.buscarPorCodBarras(codBarras);

        Estoque estoque = buscarPorCodigo(1);

        for (int i = 0; i < estoque.getProdutos().size(); i++) {
            if(estoque.getProdutos().get(i).getProduto().getCodBarras().equalsIgnoreCase(codBarras)){
                estoque.getProdutos().get(i).setQuantidade(
                        estoque.getProdutos().get(i).getQuantidade() + quantidade);
                estoqueRepository.save(estoque);
                return estoque;
            }
        }

        ProdutoEstoque pe = new ProdutoEstoque(produtoSalvo, estoque, quantidade);
        estoque.getProdutos().add(pe);

        estoqueRepository.save(estoque);

        return estoque;
    }


    //alterar quantidade de tal produto dentro do estoque:
}
