package br.com.mercado.service.impl;

import br.com.mercado.dto.ProdutoNewDTO;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.repository.ProdutoRepository;
import br.com.mercado.service.CategoriaService;
import br.com.mercado.service.ProdutoService;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private ProdutoRepository produtoRepository;

    private CategoriaService categoriaService;

    public void inserir(Produto produto){
        if(produto == null)
            throw new RuntimeException("Produto incompleto");
        produtoRepository.save(produto);
    }

    public Produto buscarPorCodigo(Integer id){
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElseThrow(() -> new ObjectNotFoundExcepction("Erro ao encontrar produto com este ID!"));
    }

    public Produto fromDTO(ProdutoNewDTO produtoNewDTO){

        List<Categoria> lista = new ArrayList<Categoria>();
        lista.add(categoriaService.buscarPorCodigo(produtoNewDTO.getIdCategoria()));

        return new Produto(null, produtoNewDTO.getNome(), produtoNewDTO.getCodBarras(), produtoNewDTO.getDataValidade(), produtoNewDTO.getValorUnitario(), lista);
    }


    public ProdutoNewDTO fromEntity(Produto produto){
        Integer idCategoria = produto.getCategorias().get(0).getId();
        return new ProdutoNewDTO(produto.getNome(),produto.getCodBarras(),
                produto.getDataValidade(), produto.getValorUnitario(), idCategoria);
    }

    public List<Produto> listarTodos() {
        List<Produto> lista = produtoRepository.findAll();

        if(lista.isEmpty()){
            throw new ObjectNotFoundExcepction("Nao existe nenhum produto!!!");
        }

        return lista;
    }


}
