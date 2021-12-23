package br.com.mercado.service;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.service.exceptions.DataIntegrityException;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoriaService {

    public Categoria buscar(Integer id);

    public Categoria inserir(Categoria categoria);

    public Categoria alterar(Categoria categoria);

    public void deletar(Integer id);

    public List<Categoria> obterTodos();

    public Page<Categoria> obterPagina(Integer page, Integer linhasPorPage, String ordenarPor, String direcao);

    public Categoria fromDTO(CategoriaDTO categoriaDTO);


    }
