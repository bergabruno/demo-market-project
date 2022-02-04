package br.com.mercado.controller;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.dto.ProdutoDTO;
import br.com.mercado.model.entity.AdminLogin;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.repository.ProdutoRepository;
import br.com.mercado.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.Arrays;
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

    private Produto gerarProduto(){
        return new Produto(5, "Arroz 5kg", "8246718",
                "20/02/2022", 23.59);
    }

    public String tokenGerado;

    @Autowired
    AdminService adminService;

    @BeforeEach
    public void tokenGenerate(){
        AdminLogin admin = new AdminLogin();
        admin.setLogin("admin");
        admin.setSenha("admin");
        admin = adminService.logar(admin);
        tokenGerado = admin.getToken();
    }


    @Test
    @DisplayName("Deve criar um produto com sucesso")
    public void deveCriarProdutoTest() throws Exception {

        Produto produtoSalvo = gerarProduto();
        produtoSalvo.getCategorias().add(new Categoria());

        ProdutoDTO produtoDTO = new ProdutoDTO(produtoSalvo);
        produtoDTO.setIdCategoria(1);

        String json = new ObjectMapper().writeValueAsString(produtoDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(produto_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json).header("Authorization", tokenGerado);


        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(produtoSalvo.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("dataValidade").value(produtoSalvo.getDataValidade()))
                .andExpect(MockMvcResultMatchers.jsonPath("valorUnitario").value(produtoSalvo.getValorUnitario()))
                .andExpect(MockMvcResultMatchers.jsonPath("codBarras").value(produtoDTO.getCodBarras()));
    }

    @Test
    @DisplayName("Deve retornar um produto pelo id")
    public void obterProdutoTest() throws Exception {

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
    @DisplayName("Deve retornar um Object not found ao buscar produto pelo id")
    public void obterProdutoFalhaTest() throws Exception {

        BDDMockito.given(produtoRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(produto_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar um produto pelo codigo de barras")
    public void obterProdutoPorCodBarrasTest() throws Exception {

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

    @Test
    @DisplayName("Deve retornar um Object not found ao buscar produto pelo codigo de barras")
    public void obterProdutoCodBarrasFalhaTest() throws Exception {

        BDDMockito.given(produtoRepository.findByCodBarras(Mockito.anyString())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(produto_API.concat("/codigoBarras/" + "32132"))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    @DisplayName("Deve obter uma lista com todos os produtos")
    public void listarTodosProdutosTest() throws Exception{
        Produto prod1 = gerarProduto();
        prod1.setId(1);
        prod1.setCategoria(new Categoria(1, "Mercearia"));
        Produto prod2 = gerarProduto();
        prod2.setId(2);
        prod2.setCategoria(new Categoria(1, "Mercearia"));


        Mockito.when(produtoRepository.findAll()).thenReturn(Arrays.asList(prod1, prod2));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(produto_API);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Deve atualizar uma categoria")
    public void atualizarCategoriaTest() throws Exception{
        Integer id = 12;

        Produto produto = gerarProduto();
        produto.setId(id);
        produto.setCategoria(new Categoria(1, "Frutas"));

        ProdutoDTO produtoDTO = new ProdutoDTO(produto);
        produtoDTO.setNome("banana");

        String json = new ObjectMapper().writeValueAsString(produtoDTO);

        Mockito.when(produtoRepository.existsById(Mockito.anyInt())).thenReturn(true);
        BDDMockito.given(produtoRepository.findById(Mockito.anyInt())).willReturn(Optional.of(produto));
        BDDMockito.given(produtoRepository.save(Mockito.any(Produto.class))).willReturn(produto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(produto_API.concat("/" + id))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", tokenGerado);


        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value("banana"));
    }

}
