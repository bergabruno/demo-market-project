package br.com.mercado.service.impl;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.service.CategoriaService;
import br.com.mercado.service.exceptions.DataIntegrityException;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    private final Logger log = Logger.getLogger("br.com.mercado.service.impl.CategoriaController");

    public Categoria buscarPorCodigo(Integer id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundExcepction("Erro ao encontrar por este codigo!"));
    }

    public Categoria inserir(Categoria categoria){
        if(categoria == null){

         }
        categoria.setId(null);
        categoriaRepository.save(categoria);
        return categoria;
    }

    public Categoria alterar(Categoria categoria){
        if(!categoriaRepository.existsById(categoria.getId()))
            throw new ObjectNotFoundExcepction("Nao existe nenhuma categoria com este ID!");

        Categoria newCat = buscarPorCodigo(categoria.getId());
        updateData(newCat, categoria);
        return categoriaRepository.save(newCat);
    }

    public void deletar(Integer id){
        if(!categoriaRepository.existsById(id))
            throw new ObjectNotFoundExcepction("Nao existe nenhuma categoria com este ID!");

        Optional<Categoria> cat = categoriaRepository.findById(id);
        if(!cat.get().getProdutos().isEmpty())
            throw new DataIntegrityException("Nao é possivel excluir uma categoria que possui produtos");
        categoriaRepository.deleteById(id);
    }

    public List<Categoria> obterTodos(){
        List<Categoria> categorias =  categoriaRepository.findAll();

        if(categorias.isEmpty())
            throw new ObjectNotFoundExcepction("Nao foi encontrado nenhuma categoria!");
        return categorias;
    }

    public Page<Categoria> obterPagina(Integer page, Integer linhasPorPage, String ordenarPor, String direcao){
        PageRequest pageRequest = PageRequest.of(page,linhasPorPage, Sort.Direction.valueOf(direcao), ordenarPor);
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO categoriaDTO){
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }

    private void updateData(Categoria newCat, Categoria categoria){
        newCat.setNome(categoria.getNome());
    }

}
