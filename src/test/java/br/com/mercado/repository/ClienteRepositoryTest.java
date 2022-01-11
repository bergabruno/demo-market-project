package br.com.mercado.repository;

import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Cliente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test-repository")
public class ClienteRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ClienteRepository clienteRepository;

    private Cliente gerarCliente() {
        return new Cliente(null, "Bruno Bergamasco", "brubinho@gmail.com", "184.825.360-52");
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um cliente com o mesmo cpf")
    public void deveRetornarTrueQuandoExistirPeloCPFTest() {

        String cpf = "928.348.560-28";

        Cliente cliente = gerarCliente();
        cliente.setCpf(cpf);

        entityManager.persist(cliente);

        boolean exists = clienteRepository.existsByCpf(cpf);

        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um cliente com o mesmo email")
    public void deveRetornarTrueQuandoExistirPeloEmailTest() {

        String email = "bruninho11d@hotmail.com";

        Cliente cliente = gerarCliente();
        cliente.setCpf(email);

        entityManager.persist(cliente);

        boolean exists = clienteRepository.existsByCpf(email);

        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve obter um cliente pelo ID")
    public void deveObterUmClientePeloIdTest(){
        Integer id = 1;

        Cliente cliente = gerarCliente();
        cliente.setId(null);

        entityManager.persist(cliente);

        Cliente novoCliente = clienteRepository.findById(cliente.getId()).get();

        Assertions.assertThat(novoCliente.getId()).isEqualTo(cliente.getId());
        Assertions.assertThat(novoCliente.getNome()).isEqualTo(cliente.getNome());
        Assertions.assertThat(novoCliente.getCpf()).isEqualTo(cliente.getCpf());
        Assertions.assertThat(novoCliente.getEmail()).isEqualTo(cliente.getEmail());
    }

    @Test
    @DisplayName("Deve retornar retornar um cliente vazio quando tentar achar por id invalido")
    public void naoDeveObterUmClientePorIdTest(){

        Optional<Cliente> cliente = clienteRepository.findById(Mockito.anyInt());

        Assertions.assertThat(cliente).isEmpty();
    }

    @Test
    @DisplayName("Deve salvar um cliente")
    public void deveSalvarUmCliente(){

        Cliente cliente = new Cliente();

        Cliente clienteSalvo = clienteRepository.save(cliente);

        Assertions.assertThat(clienteSalvo).isNotNull();

    }



}
