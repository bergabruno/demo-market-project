//package br.com.mercado.repository;
//
//import br.com.mercado.dto.CategoriaDTO;
//import br.com.mercado.model.entity.Categoria;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Optional;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@ActiveProfiles("test-repository")
//public class CategoriaRepositoryTest {
//
//    //USAR H2
//    @Autowired
//    TestEntityManager entityManager;
//
//    @Autowired
//    CategoriaRepository categoriaRepository;
//
//    @Test
//    @DisplayName("Deve retornar verdadeiro quando existir uma categoria com o nome informado")
//    public void deveRetornarTrueQuandoExistirPeloNomeTest() {
//
//        String nome = "Mercearia";
//
//        Categoria categoria = new Categoria(null, "Mercearia");
//        entityManager.persist(categoria);
//
//        boolean exists = categoriaRepository.existsByNome(nome);
//
//        Assertions.assertThat(exists).isTrue();
//    }
//
//    @Test
//    @DisplayName("Deve retornar false quando nao existir nenhuma categoria com o nome informado")
//    public void deveRetornarFalseQuandoNaoExistirPeloNomeTest() {
//
//        String nome = "Mercearia";
//
//        boolean exists = categoriaRepository.existsByNome(nome);
//
//        Assertions.assertThat(exists).isFalse();
//    }
//
//    @Test
//    @DisplayName("Deve retornar uma categoria ao buscar pelo id")
//    public void deveObterUmaCategoriaPorIdTest(){
//
//        Categoria categoria = new Categoria(null,"Limpeza");
//        categoria = entityManager.persist(categoria);
//
//        Optional<Categoria> novaCategoria = categoriaRepository.findById(categoria.getId());
//
//        Assertions.assertThat(novaCategoria.isPresent()).isTrue();
//        Assertions.assertThat(novaCategoria.get().getNome()).isEqualTo(categoria.getNome());
//        Assertions.assertThat(novaCategoria.get().getId()).isEqualTo(2);
//    }
//
//    @Test
//    @DisplayName("Deve retornar retornar uma categoria vazia quando tentar achar por id invalido")
//    public void naoDeveObterUmaCategoriaPorIdTest(){
//
//        Optional<Categoria> categoria = categoriaRepository.findById(Mockito.anyInt());
//
//        Assertions.assertThat(categoria).isEmpty();
//    }
//
//    @Test
//    @DisplayName("Deve salvar uma categoria")
//    public void deveSalvarUmaCategoria(){
//
//        Categoria categoria = new Categoria(1,"Limpeza");
//
//        Categoria categoriaSalva = categoriaRepository.save(categoria);
//
//        Assertions.assertThat(categoriaSalva).isNotNull();
//    }
//
//    @Test
//    @DisplayName("Deve deletar uma categoria")
//    public void deveDeletarUmaCategoria(){
//
//        Categoria categoria = new Categoria(null,"Limpeza");
//        entityManager.persist(categoria);
//        Categoria categoriaSalva = entityManager.find(Categoria.class, categoria.getId());
//
//        categoriaRepository.deleteById(categoriaSalva.getId());
//        Categoria categoriaNula = entityManager.find(Categoria.class, categoria.getId());
//
//        Assertions.assertThat(categoriaNula).isNull();
//    }
//
//}
