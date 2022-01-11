package br.com.mercado.service;

import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.service.exceptions.DataIntegrityException;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
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

        Integer id = 1;
        Categoria categoria = gerarCat();
        categoria.setId(1);

        Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(categoriaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(categoria));

        Categoria categoriaEncontrada = categoriaService.buscarPorCodigo(id);

        Assertions.assertThat(categoriaEncontrada).isNotNull();
        Assertions.assertThat(categoria.getId()).isEqualTo(categoriaEncontrada.getId());
        Assertions.assertThat(categoria.getNome()).isEqualTo(categoriaEncontrada.getNome());
    }

    @Test
    @DisplayName("Deve retornar um Object not found quando procurar pelo id da categoria invalido")
    public void deveObterCategoriaPeloIdFalha() {
        Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(false);

        Throwable exception = Assertions.catchThrowable(() -> categoriaService.buscarPorCodigo(Mockito.anyInt()));

        Assertions.assertThat(exception)
                .isInstanceOf(ObjectNotFoundExcepction.class)
                .hasMessage("Erro ao buscar categoria");
    }

    @Test
    @DisplayName("Deve apagar uma categoria por ID")
    public void deveApagarUmaCategoriaPorId(){

        Categoria categoria = gerarCat();

//        categoria.getProdutos().add(new Produto(1, "Farinha de trigo 1kg", "1046696",
//                "20/02/2022", 4.50));

        Mockito.when(categoriaRepository.existsById(categoria.getId())).thenReturn(true);
        categoriaService.deletar(categoria.getId());

        Mockito.verify(categoriaService, Mockito.times(1)).deletar(categoria.getId());
    }

    @Test
    @DisplayName("Nao deve apagar uma categoria por id")
    public void naoDeveApagarUmaCategoriaPorId(){
        Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(false);

        Throwable exception = Assertions.catchThrowable(() -> categoriaService.deletar(Mockito.anyInt()));

        Assertions.assertThat(exception)
                .isInstanceOf(ObjectNotFoundExcepction.class)
                .hasMessage("Nao existe nenhuma categoria com este ID!");
    }

    @Test
    @DisplayName("Deve atualizar uma categoria")
    public void deveAlterarUmaCategoria() {

        int id = 1;

        Categoria categoria = gerarCat();

        Categoria novaCategoria = new Categoria(2, "Mercearia");
        novaCategoria.setId(1);

        Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(categoriaRepository.save(categoria)).thenReturn(novaCategoria);

        Categoria cat = categoriaService.alterar(categoria);

        assertThat(cat.getId()).isEqualTo(novaCategoria);
        assertThat(cat.getNome()).isEqualTo(novaCategoria);
    }


    @Test
    @DisplayName("Nao deve alterar uma categoria por id")
    public void naoDeveAlterarUmaCategoriaPorId(){
        Categoria categoria = gerarCat();
        Mockito.when(categoriaRepository.existsById(Mockito.anyInt())).thenReturn(false);

        Throwable exception = Assertions.catchThrowable(() -> categoriaService.alterar(categoria));

        Assertions.assertThat(exception)
                .isInstanceOf(ObjectNotFoundExcepction.class)
                .hasMessage("Nao existe nenhuma categoria com este ID!");
    }
}
