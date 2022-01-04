package br.com.mercado.controller;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.print.attribute.standard.Media;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@EnableWebMvc
//@WebMvcTest ??
public class CategoriaControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CategoriaService categoriaService;

    static String categoria_API = "/api/v1/categorias";

    @Test
    @DisplayName("deve criar uma categoria com sucesso")
    public void deveCriarCategoria() throws Exception{

        CategoriaDTO categoriaDTO = new CategoriaDTO();

        categoriaDTO.setId(1);
        categoriaDTO.setNome("testeeee");

        String json = new ObjectMapper().writeValueAsString(categoriaDTO);

        MockHttpServletRequestBuilder request =  MockMvcRequestBuilders
                .post(categoria_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(categoriaDTO.getNome())) ;
    }

//    @Test
//    @DisplayName("nao deve criar uma categoria")
//    public void deveCriarCategoriaInvalida(){
//
//
//    }
}
