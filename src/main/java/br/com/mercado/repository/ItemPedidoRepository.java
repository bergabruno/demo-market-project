package br.com.mercado.repository;

import br.com.mercado.model.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

    @Modifying
   @Query(
           "update ItemPedido ip set ip.quantidade = ip.quantidade + ?1 where ip.pedido.id = ?2 and ip.produto.id = ?3")
    public void adicionarProduto(Integer quantidade, Integer pedidoId, Integer produtoId);

    @Transactional
    @Modifying
    @Query(
            "update ItemPedido ip set ip.quantidade = ip.quantidade - ?1 where ip.pedido.id = ?2 and ip.produto.id = ?3")
    public void deletarProduto(Integer quantidade, Integer pedidoId, Integer produtoId);
}
