package br.com.mercado.service;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.model.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriaService {

    public Categoria buscarPorCodigo(Integer id);

    public Categoria inserir(Categoria categoria);

    public Categoria alterar(Categoria categoria);

    public void deletar(Integer id);

    public List<Categoria> obterTodos();

    public Page<Categoria> obterPagina(Integer page, Integer linhasPorPage, String ordenarPor, String direcao);

    public Categoria fromDTO(CategoriaDTO categoriaDTO);
}
