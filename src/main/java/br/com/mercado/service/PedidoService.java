package br.com.mercado.service;

import br.com.mercado.model.entity.Pedido;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.repository.PedidoRepository;
import br.com.mercado.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscar(Integer id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        pedido.get().setCliente(pedido.get().getCliente());
        return pedido.orElseThrow(() -> new RuntimeException("Erro ao encontrar produto com este ID!"));
    }
}
