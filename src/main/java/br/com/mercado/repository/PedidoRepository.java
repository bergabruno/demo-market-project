package br.com.mercado.repository;

import br.com.mercado.model.entity.Cliente;
import br.com.mercado.model.entity.ItemPedido;
import br.com.mercado.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Integer> {

    @Query
            ("select pe from Pedido pe where pe.dataPedido = ?1")
    public List<Pedido> pedidosDiarios(LocalDate data);
}
