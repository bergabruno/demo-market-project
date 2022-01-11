package br.com.mercado.service;

import br.com.mercado.dto.PedidoDTO;
import br.com.mercado.model.entity.Pedido;

import org.springframework.stereotype.Service;

public interface PedidoService {

    public Pedido buscarPorCodigo(Integer id);

    public Pedido criarPedido(Pedido pedido);

    public Pedido addProduto(Integer idPedido, String codBarras, int quantidadeProd);

    public Pedido delProduto(Integer idPedido, String codBarras, int quantidadeProd);

    public Pedido finalizarPedido(Integer id);

    public Pedido cancelarPedido(Integer id);

    public Pedido fromDTO(PedidoDTO pedidoDTO);

    public PedidoDTO fromEntity(Pedido pedido);

    public Pedido tipoPagamento(Integer idPedido ,Integer tipo);
}
