package br.com.mercado.repository;

import br.com.mercado.model.entity.Cliente;
import br.com.mercado.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Integer> {
}
