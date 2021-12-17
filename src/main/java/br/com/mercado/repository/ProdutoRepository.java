package br.com.mercado.repository;

import br.com.mercado.model.entity.Cliente;
import br.com.mercado.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Integer> {
}
