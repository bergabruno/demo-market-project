package br.com.mercado.service.impl;

import br.com.mercado.dto.ProdutoDTO;
import br.com.mercado.dto.ProdutoNewDTO;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.repository.ProdutoRepository;
import br.com.mercado.service.CategoriaService;
import br.com.mercado.service.ProdutoService;
import br.com.mercado.service.exceptions.DataIntegrityException;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private ProdutoRepository produtoRepository;

    private CategoriaService categoriaService;


    public void inserir(Produto produto) {
        if (produtoRepository.existsByCodBarras(produto.getCodBarras()))
            throw new DataIntegrityException("Já existe um produto com este codigo de barras!");

        produtoRepository.save(produto);
    }

    public Produto buscarPorCodigo(Integer id) {

        return produtoRepository.findById(id).orElseThrow( () -> new ObjectNotFoundExcepction("Nao foi encontrado nenhum produto com esse id!"));
    }

    public List<Produto> listarTodos() {
        List<Produto> lista = produtoRepository.findAll();

        if (lista.isEmpty())
            throw new ObjectNotFoundExcepction("Nao existe nenhum produto!!!");

        return lista;
    }

    public Produto alterar(Produto produto) {
        if (!produtoRepository.existsById(produto.getId()))
            throw new ObjectNotFoundExcepction("Nao existe nenhum produto com este ID!");

        Produto newProd = buscarPorCodigo(produto.getId());
        updateData(newProd, produto);
        newProd = produtoRepository.save(newProd);
        return newProd;
    }

    public Produto buscarPorCodBarras(String codBarras) {
        if (!produtoRepository.existsByCodBarras(codBarras))
            throw new ObjectNotFoundExcepction("Nao foi encontrado nenhum produto com esse cod de barras!");

        return produtoRepository.findByCodBarras(codBarras).get();
    }

    private void updateData(Produto newProd, Produto produto) {
        newProd.setNome(produto.getNome());
        newProd.setValorUnitario(produto.getValorUnitario());
        newProd.setDataValidade(produto.getDataValidade());
        newProd.setCodBarras(produto.getCodBarras());
    }

    public Produto fromDTO(ProdutoNewDTO produtoNewDTO) {

        List<Categoria> lista = new ArrayList<Categoria>();
        lista.add(categoriaService.buscarPorCodigo(produtoNewDTO.getIdCategoria()));

        return new Produto(null, produtoNewDTO.getNome(), produtoNewDTO.getCodBarras(), produtoNewDTO.getDataValidade(), produtoNewDTO.getValorUnitario(), lista);
    }

    public Produto fromDTO(ProdutoDTO produtoDTO) {
        return new Produto(null, produtoDTO.getNome(), produtoDTO.getCodBarras(), produtoDTO.getDataValidade(), produtoDTO.getValorUnitario());
    }

    public ProdutoNewDTO fromEntity(Produto produto) {
        Integer idCategoria = produto.getCategorias().get(0).getId();

        return new ProdutoNewDTO(produto.getNome(), produto.getCodBarras(),
                produto.getDataValidade(), produto.getValorUnitario(), idCategoria);
    }

    public ProdutoDTO fromEntityDTO(Produto produto) {
        return new ProdutoDTO(produto.getId(), produto.getNome(), produto.getCodBarras(),
                produto.getDataValidade(), produto.getValorUnitario(), produto.getCategorias().get(0).getId());
    }

    public Page<Produto> obterPagina(Integer page, Integer linhasPorPage, String ordenarPor, String direcao){
        PageRequest pageRequest = PageRequest.of(page,linhasPorPage, Sort.Direction.valueOf(direcao), ordenarPor);
        return produtoRepository.findAll(pageRequest);
    }
}
