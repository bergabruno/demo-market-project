package br.com.mercado.service;

import br.com.mercado.model.entity.Cliente;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.repository.ClienteRepository;
import br.com.mercado.repository.ProdutoRepository;
import br.com.mercado.service.exceptions.NegocioException;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class ProdutoServiceTest {

    @Autowired
    ProdutoService produtoService;

    @MockBean
    @Autowired
    ProdutoRepository produtoRepository;

    private Produto gerarProduto() {
        return new Produto(1, "Arroz 5kg", "8246718",
                "20/02/2022", 23.59);
    }


}
