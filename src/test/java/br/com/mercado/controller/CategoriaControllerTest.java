package br.com.mercado.controller;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.service.CategoriaService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;


@WebMvcTest
public class CategoriaControllerTest {

    @Autowired
    private CategoriaController categoriaController;

    @MockBean
    private CategoriaService categoriaService;

//    @MockBean
//    private CategoriaRepository categoriaRepository;


    @BeforeEach
    public void setup(){
        //apenas setando para injetar somente esse controller e nao todos.
        RestAssuredMockMvc.standaloneSetup(this.categoriaController);
    }


    @Test
    public void deveRetornarSucesso_QuandoBuscarCategoria(){
        Mockito.when(this.categoriaService.buscarPorCodigo(1)).
                thenReturn(new Categoria(1, "FERRAMENTAS"));

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .when().get("api/v1/categorias/{id}", 1)
                .then().statusCode(HttpStatus.OK.value());
    }
}
