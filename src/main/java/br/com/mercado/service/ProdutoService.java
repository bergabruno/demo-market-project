package br.com.mercado.service;

import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProdutoService {

    public Produto buscar(Integer id);
    public void salvar(Produto produto);


}
