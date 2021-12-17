package br.com.mercado.repository;

import br.com.mercado.model.entity.Estoque;
import br.com.mercado.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque,Integer> {
}
