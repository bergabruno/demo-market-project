package br.com.mercado.service;

import br.com.mercado.model.entity.ItemPedido;
import br.com.mercado.model.entity.Pedido;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.repository.PedidoRepository;
import br.com.mercado.repository.ProdutoRepository;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PedidoService {

    private PedidoRepository pedidoRepository;

    private ProdutoRepository produtoRepository;


    public Pedido buscar(Integer id){
        if(!pedidoRepository.existsById(id))
            throw new ObjectNotFoundExcepction("Nao existe nenhum pedido com este ID!");


        Optional<Pedido> pedido = pedidoRepository.findById(id);
        pedido.get().setCliente(pedido.get().getCliente());
        return pedido.orElseThrow(() -> new RuntimeException("Erro ao encontrar produto com este ID!"));
    }

    @Transactional
    public Pedido salvar(Pedido pedido){
        pedidoRepository.save(pedido);
        return pedido;
    }

    //tirar o iddopedido e colocar um current
    @Transactional
    public void addProduto(Integer idPedido, String codBarras, int quantidadeProd){

        Pedido pedido = buscar(idPedido);

        Optional<Produto> produto = produtoRepository.findByCodBarras(codBarras);

        ItemPedido ip = new ItemPedido(produto.get(), pedido, 0.0, quantidadeProd, produto.get().getValorUnitario());

        pedido.getItens().add(ip);
        produto.get().getItens().add(ip);
    }
}
