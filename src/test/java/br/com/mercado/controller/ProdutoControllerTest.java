package br.com.mercado.controller;


import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.repository.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProdutoRepository produtoRepository;
    //tentar futuramente alterar para service

    static String produto_API = "/api/v1/produtos";

    @Test
    @DisplayName("Deve retornar um produto pelo id")
    public void obterCategoriaTest() throws Exception {

        Integer id = 1;

        Produto produto = new Produto();
        produto.getCategorias().add(new Categoria());
        Mockito.when(produtoRepository.existsById(Mockito.anyInt())).thenReturn(true);
        BDDMockito.given(produtoRepository.findById(id)).willReturn(Optional.of(produto));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(produto_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Deve retornar um produto pelo codigo de barras")
    public void obterCategoriaPorCodBarrasTest() throws Exception {

        String codigoBarras = "32047";

        Produto produto = new Produto();
        produto.getCategorias().add(new Categoria());
        Mockito.when(produtoRepository.existsByCodBarras(Mockito.anyString())).thenReturn(true);
        BDDMockito.given(produtoRepository.findByCodBarras(codigoBarras)).willReturn(Optional.of(produto));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(produto_API.concat("/codigoBarras/" + codigoBarras))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
