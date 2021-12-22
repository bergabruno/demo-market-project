package br.com.mercado.repository;

import br.com.mercado.model.entity.Cliente;
import br.com.mercado.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto,Integer> {


    public Optional<Produto> findByCodBarras(String codBarras);
}
