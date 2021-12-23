package br.com.mercado.service;

import br.com.mercado.dto.ClienteDTO;
import br.com.mercado.model.entity.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService {


    public Cliente buscar(Integer id);

    public Cliente inserir(Cliente cliente);

    public Cliente alterar(Cliente cliente);

    public void deletar(Integer id);

    public List<Cliente> obterTodos();

    public Cliente fromDTO(ClienteDTO clienteDTO);


}
