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
        return  new Produto(1, "Arroz 5kg", "8246718",
                "20/02/2022", 23.59);
    }

//    @Test
//    @DisplayName("Deve salvar um produto")
//    public void deveSalvarProduto() {
//        Produto produto = gerarProduto();
//
//        //REVER - MOCKITO.WHEN
//
//        //QUANDO EXECUTAR CLIENTE INSERIR, RETORNAR CLIENTE
//        Mockito.when(clienteService.inserir(cliente))
//                .thenReturn(new Cliente(1, "Bruno Bergamasco", "brubinho@gmail.com", "184.825.360-52"));
//
//        Cliente clienteSalvo = clienteService.inserir(cliente);
//
//        assertThat(clienteSalvo.getId()).isNotNull();
//        assertThat(clienteSalvo.getNome()).isEqualTo(cliente.getNome());
//    }

//    @Test
//    @DisplayName("Deve retornar erro ao tentar salvar cliente com cpf existente")
//    public void naoDeveSalvarClienteComCPFIgual() {
//        Cliente cliente = gerarCliente();
//
//        Mockito.when(clienteRepository.existsByCpf(Mockito.anyString())).thenReturn(true);
//
//        Throwable exception = Assertions.catchThrowable(() -> clienteService.inserir(cliente));
//
//        Assertions.assertThat(exception)
//                .isInstanceOf(NegocioException.class)
//                .hasMessage("CPF já em uso!");
//
//        Mockito.verify(clienteRepository, Mockito.never()).save(cliente);
//    }
//
//    @Test
//    @DisplayName("Deve obter um cliente pelo id")
//    public void deveObterClientePeloId() {
//
//        Integer id = 1;
//        Cliente cliente = gerarCliente();
//        cliente.setId(id);
//
//        Mockito.when(clienteRepository.existsById(Mockito.anyInt())).thenReturn(true);
//        Mockito.when(clienteRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(cliente));
//
//        Cliente clienteSalvo = clienteService.buscarPorCodigo(id);
//
//        Assertions.assertThat(clienteSalvo).isNotNull();
//        Assertions.assertThat(clienteSalvo.getId()).isEqualTo(id);
//        Assertions.assertThat(clienteSalvo.getEmail()).isEqualTo(cliente.getEmail());
//    }
//
//    @Test
//    @DisplayName("Deve retornar um Object not found quando procurar pelo id da cliente invalido")
//    public void deveObterClientePeloIdFalha() {
//        Mockito.when(clienteRepository.existsById(Mockito.anyInt())).thenReturn(false);
//
//        Throwable exception = Assertions.catchThrowable(() -> clienteService.buscarPorCodigo(Mockito.anyInt()));
//
//        Assertions.assertThat(exception)
//                .isInstanceOf(ObjectNotFoundExcepction.class)
//                .hasMessage("Erro ao encontrar cliente por este codigo!");
//    }
//
//    @Test
//    @DisplayName("Deve apagar um cliente por ID")
//    public void deveApagarUmClientePorId() {
//
//        Cliente cliente = gerarCliente();
//        cliente.setId(1);
//
//        Mockito.when(clienteRepository.existsById(Mockito.anyInt())).thenReturn(true);
//        Mockito.when(clienteRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(cliente));
//        clienteService.deletar(cliente.getId());
//
//        Mockito.verify(clienteRepository, Mockito.times(1)).deleteById(cliente.getId());
//    }
//
//    @Test
//    @DisplayName("Nao deve apagar um cliente com id invalido")
//    public void naoDeveApagarUmClientePorId() {
//        Mockito.when(clienteRepository.existsById(Mockito.anyInt())).thenReturn(false);
//
//        Throwable exception = Assertions.catchThrowable(() -> clienteService.deletar(Mockito.anyInt()));
//
//        Assertions.assertThat(exception)
//                .isInstanceOf(ObjectNotFoundExcepction.class)
//                .hasMessage("Erro ao encontrar cliente por este codigo!");
//    }
//
//    @Test
//    @DisplayName("Deve atualizar um cliente")
//    public void deveAlterarUmCliente() {
//
//        Integer id = 1;
//
//        Cliente cliente = gerarCliente();
//        cliente.setId(1);
//
//        Cliente clienteNovo = gerarCliente();
//        clienteNovo.setId(1);
//        clienteNovo.setNome("Juninhoooo");
//
//        Mockito.when(clienteRepository.existsById(Mockito.anyInt())).thenReturn(true);
//        Mockito.when(clienteRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(cliente));
//        Mockito.when(clienteRepository.save(cliente)).thenReturn(clienteNovo);
//
//        Cliente clienteSalvo = clienteService.alterar(cliente);
//
//        assertThat(clienteNovo.getId()).isEqualTo(clienteSalvo.getId());
//        assertThat(clienteNovo.getCpf()).isEqualTo(clienteSalvo.getCpf());
//    }
//
//
//    @Test
//    @DisplayName("Nao deve alterar um cliente por id")
//    public void naoDeveAlterarUmClientePorId() {
//        Cliente cliente = gerarCliente();
//        Mockito.when(clienteRepository.existsById(Mockito.anyInt())).thenReturn(false);
//
//        Throwable exception = Assertions.catchThrowable(() -> clienteService.alterar(cliente));
//
//        Assertions.assertThat(exception)
//                .isInstanceOf(ObjectNotFoundExcepction.class)
//                .hasMessage("Erro ao encontrar cliente por este codigo!");
//    }
}
