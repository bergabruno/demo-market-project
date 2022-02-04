package br.com.mercado.repository;

import br.com.mercado.model.entity.ProdutoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoEstoqueRepository extends JpaRepository<ProdutoEstoque,Integer> {
}
