package br.com.mercado.service;

import br.com.mercado.model.entity.Categoria;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.service.impl.CategoriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CategoriaServiceTest {

    @Autowired
    CategoriaService categoriaService;

    @MockBean
    @Autowired
    CategoriaRepository categoriaRepository;


    @Test
    @DisplayName("Deve salvar uma categoria")
    public void deveSalvarCategoria(){
        Categoria categoria = new Categoria();
        categoria.setNome("Almoxarifado");
        Mockito.when(categoriaRepository.save(categoria))
                .thenReturn(new Categoria(1, "Almoxarifado"));

        Categoria categoriaSalva = categoriaService.inserir(categoria);

        assertThat(categoriaSalva.getId()).isNotNull();
        assertThat(categoriaSalva.getNome()).isEqualTo(categoria.getNome());

    }
}
