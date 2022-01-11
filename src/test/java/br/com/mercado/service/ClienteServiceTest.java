package br.com.mercado.service;

import br.com.mercado.model.entity.Cliente;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.repository.ClienteRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ClienteServiceTest {

    @Autowired
    ClienteService clienteService;

    @MockBean
    @Autowired
    ClienteRepository clienteRepository;

    private Cliente gerarCliente() {
        return new Cliente(null, "Bruno Bergamasco", "brubinho@gmail.com", "184.825.360-52");
    }
}
