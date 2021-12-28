package br.com.mercado.service.impl;

import br.com.mercado.dto.PedidoDTO;
import br.com.mercado.model.entity.Cliente;
import br.com.mercado.model.entity.ItemPedido;
import br.com.mercado.model.entity.Pedido;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.model.entity.enums.StatusPedido;
import br.com.mercado.model.entity.enums.TipoPagamento;
import br.com.mercado.repository.ClienteRepository;
import br.com.mercado.repository.ItemPedidoRepository;
import br.com.mercado.repository.PedidoRepository;
import br.com.mercado.repository.ProdutoRepository;
import br.com.mercado.service.PedidoService;
import br.com.mercado.service.exceptions.DataIntegrityException;
import br.com.mercado.service.exceptions.NegocioException;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private PedidoRepository pedidoRepository;

    private ProdutoRepository produtoRepository;

    private ClienteRepository clienteRepository;

    private ItemPedidoRepository itemPedidoRepository;

    private final Logger log = Logger.getLogger("br.com.mercado.service.impl.PedidoServiceImpl");

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        if (pedido.getCliente() != null) {
            if(!clienteRepository.existsByCpf(pedido.getCliente().getCpf())){
                throw new NegocioException("CPF Inválido ou nao encontrado no nosso banco de dados!");
            }
            Cliente cliente = clienteRepository.findByCpf(pedido.getCliente().getCpf()).get();
            cliente.getPedidos().add(pedido);
            pedido.setCliente(cliente);
        }

        pedidoRepository.save(pedido);
        return pedido;
    }

    public Pedido buscarPorCodigo(Integer id) {
        if (!pedidoRepository.existsById(id))
            throw new ObjectNotFoundExcepction("Nao existe nenhum pedido com este ID!");

        Optional<Pedido> pedido = pedidoRepository.findById(id);
        pedido.get().setCliente(pedido.get().getCliente());
        return pedido.orElseThrow(() -> new RuntimeException("Erro ao encontrar produto com este ID!"));
    }

    //tirar o iddopedido e colocar um current
    @Transactional
    public Pedido addProduto(Integer idPedido, String codBarras, int quantidadeProd) {

        Pedido pedido = buscarPorCodigo(idPedido);

        if (!pedido.getStatusPedido().equals(StatusPedido.EM_ANDAMENTO))
            throw new RuntimeException("Nao é possivel inserir um produto em um pedido que nao está em andamento");

        if (produtoRepository.findByCodBarras(codBarras).isEmpty())
            throw new ObjectNotFoundExcepction("Nao existe nenhum produto com este cod de barras");

        if (pedido.getStatusPedido().getCod() != 3)
            throw new RuntimeException("Nao é possivel inserir produtos em um pedido finalizado/cancelado!");

        Optional<Produto> produto = produtoRepository.findByCodBarras(codBarras);

        //fazendo a soma do produto caso ja tenha esse produto
        Pedido pedidoSomado = somarOuSubtrairProd(pedido, produto.get().getId(), quantidadeProd, "+");

        if (pedidoSomado != null) {
            return pedidoSomado;
        }

        ItemPedido ip = new ItemPedido(produto.get(), pedido, 0.0, quantidadeProd, produto.get().getValorUnitario());

        pedido.getItens().add(ip);
        produto.get().getItens().add(ip);

        return pedido;
    }

    public Pedido delProduto(Integer idPedido, String codBarras, int quantidadeProd) {
        Pedido pedido = buscarPorCodigo(idPedido);

        Optional<Produto> produto = produtoRepository.findByCodBarras(codBarras);

        Pedido pedidoSomado = somarOuSubtrairProd(pedido, produto.get().getId(), quantidadeProd, "-");

        if (pedidoSomado != null) {
            return pedidoSomado;
        }

        throw new ObjectNotFoundExcepction("Nao foi encontrado nenhum produto com este codigo de barras no pedido!");
    }


    public Pedido finalizarPedido(Integer id) {
        Pedido pedido = buscarPorCodigo(id);

        if (pedido.getItens().isEmpty())
            throw new DataIntegrityException("Nao é possivel encerrar um pedido sem nenhum produto, cancele-o!");
        if (pedido.getStatusPedido().equals(StatusPedido.CANCELADO))
            throw new DataIntegrityException("Nao é possivel finalizar um pedido que foi cancelado!");
        if (pedido.getTipoPagamento() == null)
            throw new DataIntegrityException("Nao é possivel finalizar um pedido que nao tem forma de pagamento!");

        pedido.setStatusPedido(StatusPedido.FINALIZADO);

        pedidoRepository.save(pedido);

        return pedido;
    }

    public Pedido cancelarPedido(Integer id) {
        Pedido pedido = buscarPorCodigo(id);

        pedido.setStatusPedido(StatusPedido.CANCELADO);

        pedidoRepository.save(pedido);

        return pedido;
    }

    public Pedido fromDTO(PedidoDTO pedidoDTO) {
        return new Pedido(pedidoDTO.getId(), pedidoDTO.getCliente(), StatusPedido.EM_ANDAMENTO, LocalDate.now());
    }

    @Override
    public Pedido tipoPagamento(Integer idPedido, Integer tipo) {
        Pedido pedido = buscarPorCodigo(idPedido);

        pedido.setTipoPagamento(TipoPagamento.toEnum(tipo));
        //atualizando o pedido apos mudar o tipo de pagamento
        pedidoRepository.save(pedido);

        return pedido;
    }

    private Pedido somarOuSubtrairProd(Pedido pedido, Integer idProduto, int quantidadeProd, String sinal) {
        for (int i = 0; i < pedido.getItens().size(); i++) {
            if (pedido.getItens().get(i).getProduto().getId().equals(idProduto)) {
                ItemPedido ip2 = itemPedidoRepository.acharItemPedido(pedido.getId(), idProduto).get();
                if (sinal.equalsIgnoreCase("+")) {
                    ip2.setQuantidade(ip2.getQuantidade() + quantidadeProd);
                } else {
                    if(quantidadeProd > ip2.getQuantidade())
                        throw new NegocioException("Quantidade para ser retirada é maior do que a do pedido!");
                    ip2.setQuantidade(ip2.getQuantidade() - quantidadeProd);
                }
                itemPedidoRepository.save(ip2);
                pedidoRepository.save(pedido);
                return pedido;
            }
        }
        return null;
    }

}
