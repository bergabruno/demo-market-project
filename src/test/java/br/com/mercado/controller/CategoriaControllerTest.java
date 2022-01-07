package br.com.mercado.controller;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.service.CategoriaService;
import br.com.mercado.service.exceptions.DataIntegrityException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
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
//@WebMvcTest ??
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoriaRepository categoriaRepository;
    //tentar futuramente alterar para service

    static String categoria_API = "/api/v1/categorias";

    private CategoriaDTO criarCategoriaDTO() {
        return new CategoriaDTO(null, "Almoxarifado");
    }

    private Categoria criarCategoria(){
        return new Categoria(null, "Almoxarifado");
    }

    @Test
    @DisplayName("Deve criar uma categoria com sucesso")
    public void deveCriarCategoria() throws Exception {
        CategoriaDTO categoriaDTO = criarCategoriaDTO();

        Categoria saveCat = new Categoria(1, categoriaDTO.getNome());

        //REVER - GIVEN
        BDDMockito.given(categoriaRepository.save(Mockito.any(Categoria.class))).willReturn(saveCat);

        String json = new ObjectMapper().writeValueAsString(categoriaDTO);

        //determinando o metodo e a URL que fará a request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(categoria_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(categoriaDTO.getNome()));
    }

    @Test
    @DisplayName("Nao deve criar uma categoria sem dados")
    public void deveCriarCategoriaInvalida() throws Exception {

        String json = new ObjectMapper().writeValueAsString(new CategoriaDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(categoria_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(1)));
    }

    @Test
    @DisplayName("Deve lancar erro ao tentar cadastrar uma categoria com o mesmo nome")
    public void deveDarErroAoCriarCategoriaComMesmoNome() throws Exception {

        CategoriaDTO categoriaDTO = criarCategoriaDTO();
        String json = new ObjectMapper().writeValueAsString(categoriaDTO);

        BDDMockito.given(categoriaRepository.save(Mockito
                .any(Categoria.class))).willThrow(new DataIntegrityException("Já existe uma categoria com este nome!"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(categoria_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(1)));
    }

    @Test
    @DisplayName("Deve retornar uma categoria pelo id")
    public void obterCategoriaTest() throws Exception {

        Integer id = 1;

        Categoria categoria = new Categoria(id, "Mercearia");
        Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(true);
        BDDMockito.given(categoriaRepository.findById(id)).willReturn(Optional.of(categoria));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(categoria_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(categoria.getNome()));
    }

    @Test
    @DisplayName("Deve retornar Not Found ao nao encontrar categoria")
    public void obterCategoriaFalhaTest() throws Exception {
        BDDMockito.given(categoriaRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(categoria_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar uma categoria")
    public void deveDeletarUmaCategoriaTest() throws Exception{

        Categoria categoria = new Categoria(1, "Limpeza");

        Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(true);
        BDDMockito.given(categoriaRepository.findById(Mockito.anyInt())).willReturn(Optional.of(categoria));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(categoria_API.concat("/" + 1));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar Object Not Found ao deletar livro")
    public void deveDeletarUmaCategoriaNotFoundTest() throws Exception{

        Categoria categoria = new Categoria(1, "Limpeza");

        Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(false);
        BDDMockito.given(categoriaRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(categoria_API.concat("/" + 1));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar uma categoria")
    public void atualizarCategoriaTest() throws Exception{
        Integer id = 12;

        Categoria categoria = criarCategoria();
        categoria.setId(12);

        String json = new ObjectMapper().writeValueAsString(categoria);

        Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(true);
        BDDMockito.given(categoriaRepository.findById(id)).willReturn(Optional.of(categoria));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(categoria_API.concat("/" + id))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value("Almoxarifado"));
    }

    @Test
    @DisplayName("Deve retornar Object Not Found ao atualizar categoria")
    public void atualizarCategoriaNotFoundTest() throws Exception{

            Integer id = 12;

            Categoria categoria = criarCategoria();
            categoria.setId(12);

            String json = new ObjectMapper().writeValueAsString(categoria);

            Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(false);
            BDDMockito.given(categoriaRepository.findById(id)).willReturn(Optional.empty());

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                    .put(categoria_API.concat("/" + id))
                    .content(json)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON);

            mvc.perform(request)
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar um erro de nome obrigario")
    public void alterarCategoriaSemNome() throws Exception{

        Integer id = 12;

        Categoria categoria = criarCategoria();
        categoria.setId(12);
        categoria.setNome(null);

        String json = new ObjectMapper().writeValueAsString(categoria);

        Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(true);
        BDDMockito.given(categoriaRepository.findById(id)).willReturn(Optional.of(categoria));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(categoria_API.concat("/" + id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(1)));
    }

}

