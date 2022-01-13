package br.com.mercado.controller;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.dto.PedidoDTO;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.ItemPedido;
import br.com.mercado.model.entity.Pedido;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.model.entity.enums.StatusPedido;
import br.com.mercado.model.entity.enums.TipoPagamento;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.repository.PedidoRepository;
import br.com.mercado.repository.ProdutoRepository;
import br.com.mercado.service.PedidoService;
import br.com.mercado.service.exceptions.DataIntegrityException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.swing.text.html.Option;
import java.util.Optional;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@EnableWebMvc
@ActiveProfiles("test")
//@WebMvcTest ??
public class PedidoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PedidoRepository pedidoRepository;
    //tentar futuramente alterar para service
    @MockBean
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoService pedidoService;

    static String pedido_API = "/api/v1/pedidos";

    @Test
    @DisplayName("Deve retornar um pedido pelo id")
    public void obterPedidoTest() throws Exception {

        Integer id = 1;

        Pedido pedido = new Pedido();
        pedido.setStatusPedido(StatusPedido.EM_ANDAMENTO);
        Mockito.when(pedidoRepository.existsById(Mockito.anyInt())).thenReturn(true);
        BDDMockito.given(pedidoRepository.findById(id)).willReturn(Optional.of(pedido));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(pedido_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DisplayName("Deve retornar um Object Not Found")
    public void obterPedidoFalhaTest() throws Exception {

        Integer id = 1;

        Mockito.when(pedidoRepository.existsById(Mockito.anyInt())).thenReturn(false);
        BDDMockito.given(pedidoRepository.findById(id)).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(pedido_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Erro ao encontrar pedido com este ID!"));
    }

    @Test
    @DisplayName("Deve criar um Pedido")
    public void deveCriarPedido() throws Exception{

        PedidoDTO pedidoDTO = new PedidoDTO();

        Pedido pedido = new Pedido();
        pedido.setStatusPedido(StatusPedido.EM_ANDAMENTO);

        BDDMockito.given(pedidoRepository.save(Mockito.any(Pedido.class))).willReturn(pedido);

        String json = new ObjectMapper().writeValueAsString(pedidoDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(pedido_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @DisplayName("Deve obter um Pedido por codigo")
    public void deveObterPedido() throws Exception{

        PedidoDTO pedidoDTO = new PedidoDTO();

        Pedido pedido = new Pedido();
        pedido.setStatusPedido(StatusPedido.EM_ANDAMENTO);
        pedido.setTipoPagamento(TipoPagamento.DINHEIRO);

        Mockito.when(pedidoRepository.existsById(Mockito.anyInt())).thenReturn(true);
        BDDMockito.given(pedidoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(pedido));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(pedido_API.concat("/" + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(pedidoRepository, Mockito.times(1)).findById(Mockito.anyInt());
    }

    @Test
    @DisplayName("Deve adicionar um produto ao pedido")
    public void deveAddProdutoTest() throws Exception{

        PedidoDTO pedidoDTO = new PedidoDTO();

        Pedido pedido = new Pedido();
        pedido.setStatusPedido(StatusPedido.EM_ANDAMENTO);

        Produto produto = new Produto(1, "Arroz 5kg", "8246718",
                "20/02/2022", 23.59);
        produto.getCategorias().add(new Categoria());

        Mockito.when(produtoRepository.existsByCodBarras(Mockito.anyString())).thenReturn(true);
        Mockito.when(produtoRepository.findByCodBarras(Mockito.anyString())).thenReturn(Optional.of(produto));

        Mockito.when(pedidoRepository.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(pedidoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(pedido));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(pedido_API.concat("/adicionar-produto/{idPedido}/{codBarras}?quantidadeProd=5" ), "1", "001");

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

//    @Test
//    @DisplayName("Deve deletar um produto ao pedido")
//    public void deveDelrodutoTest() throws Exception{
//
//        PedidoDTO pedidoDTO = new PedidoDTO();
//
//        Pedido pedido = new Pedido();
//        pedido.setStatusPedido(StatusPedido.EM_ANDAMENTO);
//
//        Produto produto = new Produto(1, "Arroz 5kg", "8246718",
//                "20/02/2022", 23.59);
//        produto.getCategorias().add(new Categoria());
//
//        ItemPedido ip = new ItemPedido(produto, pedido, 0.0, 6, 5.90);
//
//        pedido.getItens().add(ip);
//
//        Mockito.when(produtoRepository.existsByCodBarras(Mockito.anyString())).thenReturn(true);
//        Mockito.when(produtoRepository.findByCodBarras(Mockito.anyString())).thenReturn(Optional.of(produto));
//
//        Mockito.when(pedidoRepository.existsById(Mockito.anyInt())).thenReturn(true);
//        Mockito.when(pedidoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(pedido));
//
//        Mockito.when(pedidoService.delProduto(1,"324243", 5)).thenReturn(pedido);
//
//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .put(pedido_API.concat("/deletar-produto/{idPedido}/{codBarras}?quantidadeProd=5" ), "1", "001");
//
//        mvc.perform(request)
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }

    @Test
    @DisplayName("Deve finalizar um pedido")
    public void deveFinalizarPedido() throws Exception{

        Pedido pedido = new Pedido();
        pedido.setStatusPedido(StatusPedido.EM_ANDAMENTO);
        pedido.setTipoPagamento(TipoPagamento.DINHEIRO);
        pedido.setId(1);

        Produto produto = new Produto(1, "Arroz 5kg", "8246718",
                "20/02/2022", 23.59);
        produto.getCategorias().add(new Categoria());

        ItemPedido ip = new ItemPedido(produto, pedido, 0.0, 6, 5.90);

        pedido.getItens().add(ip);

        Mockito.when(pedidoRepository.existsById(Mockito.anyInt())).thenReturn(true);
        BDDMockito.given(pedidoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(pedido));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(pedido_API.concat("/finalizar/" + pedido.getId()));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("Deve cancelar um pedido")
    public void deveCancelarPedido() throws Exception{

        Pedido pedido = new Pedido();
        pedido.setStatusPedido(StatusPedido.EM_ANDAMENTO);
        pedido.setTipoPagamento(TipoPagamento.DINHEIRO);
        pedido.setId(1);

        Mockito.when(pedidoRepository.existsById(Mockito.anyInt())).thenReturn(true);
        BDDMockito.given(pedidoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(pedido));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(pedido_API.concat("/cancelar/" + pedido.getId()));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Deve mudar o tipo de pagamento um pedido")
    public void deveMudarPagamentoPedido() throws Exception{

        Pedido pedido = new Pedido();
        pedido.setStatusPedido(StatusPedido.EM_ANDAMENTO);
        pedido.setTipoPagamento(TipoPagamento.DINHEIRO);
        pedido.setId(1);

        Mockito.when(pedidoRepository.existsById(Mockito.anyInt())).thenReturn(true);
        BDDMockito.given(pedidoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(pedido));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(pedido_API.concat("/alterar-pagamento/2?status=2"));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



}

