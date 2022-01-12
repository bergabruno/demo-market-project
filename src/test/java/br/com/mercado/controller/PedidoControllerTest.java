package br.com.mercado.controller;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.dto.PedidoDTO;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Pedido;
import br.com.mercado.model.entity.enums.StatusPedido;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.repository.PedidoRepository;
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




}

