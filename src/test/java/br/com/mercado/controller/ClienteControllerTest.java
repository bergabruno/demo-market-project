package br.com.mercado.controller;

import br.com.mercado.dto.ClienteDTO;
import br.com.mercado.dto.ClienteNewDTO;
import br.com.mercado.model.entity.AdminLogin;
import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Cliente;
import br.com.mercado.model.entity.Cliente;
import br.com.mercado.repository.ClienteRepository;
import br.com.mercado.service.AdminService;
import br.com.mercado.service.CategoriaService;
import br.com.mercado.service.ClienteService;
import br.com.mercado.service.exceptions.DataIntegrityException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@EnableWebMvc
@ActiveProfiles("test")
public class ClienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;
    //tentar futuramente alterar para service

    static String cliente_API = "/api/v1/clientes";

    public String tokenGerado;

    @Autowired
    AdminService adminService;

    @BeforeEach
    public void tokenGenerate(){
        AdminLogin admin = new AdminLogin();
        admin.setLogin("admin");
        admin.setSenha("admin");
        admin = adminService.logar(admin);
        tokenGerado = admin.getToken();
    }

    private Cliente gerarCliente() {
        return new Cliente(null, "Bruno Bergamasco", "brubinho@gmail.com", "184.825.360-52");
    }

    private ClienteDTO gerarClienteDTO() {
        return new ClienteDTO(null, "brubinho@gmail.com", "184.825.360-52");
    }

    private ClienteNewDTO gerarClienteNewDTO() {
        return new ClienteNewDTO(null, "Bruno Bergamasco", "184.825.360-52", "brubinho@gmail.com");
    }

    @Test
    @DisplayName("Deve criar um cliente com sucesso")
    public void deveCriarClienteTest() throws Exception {

        ClienteNewDTO clienteDTO = gerarClienteNewDTO();

        Cliente clienteSalvo = new Cliente(1, "Bruno Bergamasco", "brubinho@gmail.com", "184.825.360-52");

        //REVER - GIVEN
        BDDMockito.given(clienteRepository.save(Mockito.any(Cliente.class))).willReturn(clienteSalvo);

        String json = new ObjectMapper().writeValueAsString(clienteDTO);

        //determinando o metodo e a URL que fará a request
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(cliente_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json).header("Authorization", tokenGerado);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(clienteDTO.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("cpf").value(clienteDTO.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(clienteDTO.getEmail()));
    }

    @Test
    @DisplayName("Nao deve criar um cliente com email igual")
    public void naoDeveCriarClienteComEmailIgualTest() throws Exception {

        ClienteNewDTO clienteDTO = gerarClienteNewDTO();
        String json = new ObjectMapper().writeValueAsString(clienteDTO);

        BDDMockito.given(clienteRepository.save(Mockito
                .any(Cliente.class))).willThrow(new DataIntegrityException("Já existe um cliente com este email!"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(cliente_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json).header("Authorization", tokenGerado);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Nao deve criar um cliente com cpf igual")
    public void naoDeveCriarClienteComCpfIgualTest() throws Exception {

        ClienteNewDTO clienteDTO = gerarClienteNewDTO();
        String json = new ObjectMapper().writeValueAsString(clienteDTO);

        BDDMockito.given(clienteRepository.save(Mockito
                .any(Cliente.class))).willThrow(new DataIntegrityException("Já existe um cliente com este CPF!"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(cliente_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("deve obter cliente por ID")
    public void deveObterClienteIdTest() throws Exception {

        Integer id = 1;

        Cliente cliente = gerarCliente();
        BDDMockito.given(clienteRepository.findById(id)).willReturn(Optional.of(cliente));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(cliente_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON).header("Authorization", tokenGerado);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(cliente.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("cpf").value(cliente.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(cliente.getEmail()));
    }

    @Test
    @DisplayName("Deve retornar Not Found ao nao encontrar cliente")
    public void obterCategoriaFalhaTest() throws Exception {

        BDDMockito.given(clienteRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(cliente_API.concat("/" + Mockito.anyInt()))
                .accept(MediaType.APPLICATION_JSON).header("Authorization", tokenGerado);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Deve obter uma lista com todos os clientes")
    public void listarTodosClientesTest() throws Exception{
        Cliente cliente1 = gerarCliente();
        cliente1.setId(1);
        Cliente cliente2 = gerarCliente();
        cliente2.setId(2);

        Mockito.when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(cliente_API).header("Authorization", tokenGerado);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Deve deletar um cliente")
    public void deveDeletarUmCliente() throws Exception{

        Cliente cliente = gerarCliente();

        Mockito.when(clienteRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(cliente));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(cliente_API.concat("/" + 1)).header("Authorization", tokenGerado);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


}
