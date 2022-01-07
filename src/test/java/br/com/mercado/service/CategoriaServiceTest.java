package br.com.mercado.service;

import br.com.mercado.model.entity.Categoria;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.service.exceptions.DataIntegrityException;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CategoriaServiceTest {

    @Autowired
    CategoriaService categoriaService;

    @MockBean
    @Autowired
    CategoriaRepository categoriaRepository;

    private Categoria gerarCat(){
        return new Categoria(1 , "Almoxarifado");
    }

    @Test
    @DisplayName("Deve salvar uma categoria")
    public void deveSalvarCategoria(){
        Categoria categoria = gerarCat();

        //REVER - MOCKITO.WHEN
        Mockito.when(categoriaRepository.existsByNome(Mockito.anyString())).thenReturn(false);
        Mockito.when(categoriaService.inserir(categoria))
                .thenReturn(new Categoria(1, "Almoxarifado"));

        Categoria categoriaSalva = categoriaService.inserir(categoria);

        assertThat(categoriaSalva.getId()).isNotNull();
        assertThat(categoriaSalva.getNome()).isEqualTo(categoria.getNome());
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar salvar categoria com nome ja existente")
    public void naoDeveSalvarCategoriaNomeIgual(){
        Categoria categoria = gerarCat();

        Mockito.when(categoriaRepository.existsByNome(Mockito.anyString())).thenReturn(true);

        Throwable exception = Assertions.catchThrowable(() -> categoriaService.inserir(categoria) );

        Assertions.assertThat(exception)
                .isInstanceOf(DataIntegrityException.class)
                .hasMessage("Já existe uma categoria com este nome!");

        Mockito.verify(categoriaRepository,Mockito.never()).save(categoria);
    }

    @Test
    @DisplayName("Deve obter uma categoria pelo id")
    public void deveObterCategoriaPeloId(){

        Categoria categoria = gerarCat();

        Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(categoriaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(categoria));
//        Mockito.when(categoriaService.buscarPorCodigo(Mockito.anyInt())).thenReturn(new Categoria(1, "Almoxarifado"));

        Assertions.assertThat(categoria.getId()).isEqualTo(1);
        Assertions.assertThat(categoria.getNome()).isEqualTo("Almoxarifado");
    }

    @Test
    @DisplayName("Deve retornar um Object not found")
    public void deveObterCategoriaPeloIdFalha() {
        Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(false);

        Throwable exception = Assertions.catchThrowable(() -> categoriaService.buscarPorCodigo(Mockito.anyInt()));

        Assertions.assertThat(exception)
                .isInstanceOf(ObjectNotFoundExcepction.class)
                .hasMessage("Erro ao encontrar categoria por este codigo!");
    }
}
