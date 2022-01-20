//package br.com.mercado.repository;
//
//import br.com.mercado.model.entity.Produto;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Optional;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@ActiveProfiles("test-repository")
//public class ProdutoRepositoryTest {
//
//    @Autowired
//    TestEntityManager entityManager;
//
//    @Autowired
//    ProdutoRepository produtoRepository;
//
//    private Produto gerarProduto(){
//        return new Produto(5, "Arroz 5kg", "8246718",
//                "20/02/2022", 23.59);
//    }
//
//    @Test
//    @DisplayName("deve obter produto por id")
//    public void deveObterProdutoPorId(){
//
//        Produto produto = gerarProduto();
//        produto.setId(null);
//
//        entityManager.persist(produto);
//
//        Optional<Produto> produtoSalvo = produtoRepository.findById(produto.getId());
//
//        Assertions.assertThat(produtoSalvo).isNotEmpty();
//        Assertions.assertThat(produtoSalvo.get().getNome()).isEqualTo(produto.getNome());
//    }
//
//    @Test
//    @DisplayName("Deve obter falso ao buscar por um id que nao existe")
//    public void deveObterFalsoAoBuscarPorId(){
//
//        boolean checar = produtoRepository.existsById(Mockito.anyInt());
//
//        Assertions.assertThat(checar).isFalse();
//    }
//
//    @Test
//    @DisplayName("Deve obter falso ao buscar por um codigo de barras que nao existe")
//    public void deveObterFalsoAoBuscarPorCodBarras(){
//
//        boolean checar = produtoRepository.existsById(Mockito.anyInt());
//
//        Assertions.assertThat(checar).isFalse();
//    }
//
//    @Test
//    @DisplayName("Deve obter verdadeiro ao buscar por um codigo de barras")
//    public void deveObterVerdadeiroAoBuscarPorCodBarras(){
//
//        Produto produto = gerarProduto();
//        produto.setId(null);
//
//        entityManager.persist(produto);
//
//        boolean checar = produtoRepository.existsByCodBarras(produto.getCodBarras());
//
//        Assertions.assertThat(checar).isTrue();
//    }
//
//    @Test
//    @DisplayName("Deve obter verdadeiro ao buscar por um ID")
//    public void deveObterVerdadeiroAoBuscarPorId(){
//
//        Produto produto = gerarProduto();
//        produto.setId(null);
//
//        entityManager.persist(produto);
//
//        boolean checar = produtoRepository.existsById(produto.getId());
//
//        Assertions.assertThat(checar).isTrue();
//    }
//
//    @Test
//    @DisplayName("deve obter produto por codigo de barras")
//    public void deveObterProdutoPorCodBarras(){
//
//        Produto produto = gerarProduto();
//        produto.setId(null);
//
//        entityManager.persist(produto);
//
//        Optional<Produto> produtoSalvo = produtoRepository.findByCodBarras(produto.getCodBarras());
//
//        Assertions.assertThat(produtoSalvo).isNotEmpty();
//        Assertions.assertThat(produtoSalvo.get().getNome()).isEqualTo(produto.getNome());
//    }
//
//    @Test
//    @DisplayName("deve salvar um produto")
//    public void deveSalvarProduto(){
//
//        Produto produto = gerarProduto();
//
//        Produto produtoSalvo = produtoRepository.save(produto);
//
//        Assertions.assertThat(produtoSalvo).isNotNull();
//    }
//
//    @Test
//    @DisplayName("deve atualizar um produto")
//    public void deveAtualizarProduto(){
//        Produto produto = gerarProduto();
//        produto.setId(null);
//
//        Produto produtoAlterarado = entityManager.persist(produto);
//
//        produtoAlterarado.setCodBarras("344355");
//        produtoAlterarado.setDataValidade("21/07/2023");
//
//        Produto produtoSalvo = produtoRepository.save(produtoAlterarado);
//
//        Assertions.assertThat(produtoSalvo).isNotNull();
//        Assertions.assertThat(produtoSalvo.getId()).isEqualTo(produto.getId());
//    }
//}
