package br.com.mercado.service;

import br.com.mercado.model.entity.Pedido;

import org.springframework.stereotype.Service;


@Service
public interface PedidoService {

    public Pedido buscar(Integer id);

    public Pedido salvar(Pedido pedido);

    public void addProduto(Integer idPedido, String codBarras, int quantidadeProd);

}
