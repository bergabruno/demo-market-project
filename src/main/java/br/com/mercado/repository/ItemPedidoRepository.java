package br.com.mercado.repository;

import br.com.mercado.model.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

    @Query
            ("select ip from ItemPedido ip where ip.pedido.id = ?1 and ip.produto.id = ?2")
    public Optional<ItemPedido> acharItemPedido(Integer idPedido, Integer idProduto);
}
