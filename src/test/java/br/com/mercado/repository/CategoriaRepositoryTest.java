package br.com.mercado.repository;

import br.com.mercado.dto.CategoriaDTO;
import br.com.mercado.model.entity.Categoria;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoriaRepositoryTest {


    //USAR H2
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir uma categoria com o nome informado")
    public void deveRetornarTrue() {

        String nome = "Mercearia";

        Categoria categoria = new Categoria(null, "Mercearia");
        entityManager.persist(categoria);

        boolean exists = categoriaRepository.existsByNome(nome);

        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando nao existir nenhuma categoria com o nome informado")
    public void deveRetornarFalse() {

        String nome = "Mercearia";

        boolean exists = categoriaRepository.existsByNome(nome);

        Assertions.assertThat(exists).isFalse();
    }



}
