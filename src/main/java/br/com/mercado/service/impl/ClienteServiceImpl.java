package br.com.mercado.service.impl;

import br.com.mercado.dto.ClienteDTO;
import br.com.mercado.dto.ClienteNewDTO;
import br.com.mercado.model.entity.Cliente;
import br.com.mercado.repository.ClienteRepository;
import br.com.mercado.service.ClienteService;
import br.com.mercado.service.exceptions.DataIntegrityException;
import br.com.mercado.service.exceptions.NegocioException;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente inserir(Cliente cliente){
        cliente.setId(null);

        if(clienteRepository.existsByEmail(cliente.getEmail()))
            throw new NegocioException("Email já em uso!");
        if(clienteRepository.existsByCpf(cliente.getCpf()))
            throw new NegocioException("CPF já em uso!");

        cliente = clienteRepository.save(cliente);
        return cliente;
    }

    public Cliente buscarPorCodigo(Integer id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundExcepction("Erro ao encontrar cliente por este codigo!"));
    }

    public List<Cliente> obterTodos(){
        List<Cliente> clientes =  clienteRepository.findAll();

        if(clientes.isEmpty())
            throw new ObjectNotFoundExcepction("Nao foi encontrado nenhum cliente!");
        return clientes;
    }

    public Cliente alterar(Cliente cliente){
        Cliente newCliente = buscarPorCodigo(cliente.getId());
        updateData(newCliente, cliente);
        return clienteRepository.save(newCliente);
    }

    public void deletar(Integer id){
        Cliente cliente = buscarPorCodigo(id);

        if(!cliente.getPedidos().isEmpty())
            throw new DataIntegrityException("Nao é possivel excluir um cliente com pedidos!");

        clienteRepository.deleteById(id);
    }


    public Cliente fromDTO(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail());
    }

    public Cliente fromDTO(ClienteNewDTO clienteDTO){
        return new Cliente(null ,clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getCpf());
    }

    private void updateData(Cliente newCliente, Cliente cliente){
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }
}
