package br.com.mercado.repository;

import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Cliente;
import br.com.mercado.model.entity.Produto;
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
    @DisplayName("Deve retornar falso quando nao existir um cliente com o email")
    public void deveRetornarFalseQuandoNaoExistirPeloEmailTest() {

        boolean exists = clienteRepository.existsByCpf(Mockito.anyString());

        Assertions.assertThat(exists).isFalse();
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

        Cliente cliente = gerarCliente();

        Cliente clienteSalvo = clienteRepository.save(cliente);

        Assertions.assertThat(clienteSalvo).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar um cliente")
    public void deveDeletarUmCliente(){

        Cliente cliente = gerarCliente();
        entityManager.persist(cliente);
        Cliente clienteSalvo = entityManager.find(Cliente.class, cliente.getId());

        clienteRepository.deleteById(clienteSalvo.getId());
        Cliente clienteNull = entityManager.find(Cliente.class, cliente.getId());

        Assertions.assertThat(clienteNull).isNull();
    }

    @Test
    @DisplayName("deve atualizar um cliente")
    public void deveAtualizarCliente(){
        Cliente cliente = gerarCliente();
        cliente.setId(null);

        Cliente clienteAlterado = entityManager.persist(cliente);
       clienteAlterado.setNome("Julia");

        Cliente clienteSalvo = clienteRepository.save(clienteAlterado);

        Assertions.assertThat(clienteSalvo).isNotNull();
        Assertions.assertThat(clienteSalvo.getId()).isEqualTo(clienteAlterado.getId());
        Assertions.assertThat(clienteSalvo.getNome()).isEqualTo(clienteAlterado.getNome());
    }

}
