package br.com.mercado.service;

import br.com.mercado.dto.ClienteDTO;
import br.com.mercado.model.entity.Cliente;
import br.com.mercado.service.exceptions.DataIntegrityException;
import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import br.com.mercado.model.entity.Cliente;
import br.com.mercado.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public Cliente buscar(Integer id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundExcepction("Erro ao encontrar por este codigo!"));
    }

    public Cliente alterar(Cliente cliente){
        if(!clienteRepository.existsById(cliente.getId()))
            throw new ObjectNotFoundExcepction("Nao existe nenhum cliente com este ID!");

        Cliente newCliente = buscar(cliente.getId());
        updateData(newCliente, cliente);
        return clienteRepository.save(newCliente);
    }

    public void deletar(Integer id){
        if(!clienteRepository.existsById(id))
            throw new ObjectNotFoundExcepction("Nao existe nenhum cliente com este ID!");

        Cliente cliente = buscar(id);
        if(!cliente.getPedidos().isEmpty())
            throw new DataIntegrityException("Nao é possivel excluir um cliente com pedidos!");

        clienteRepository.deleteById(id);
    }

    public List<Cliente> obterTodos(){
        List<Cliente> clientes =  clienteRepository.findAll();

        if(clientes.isEmpty())
            throw new ObjectNotFoundExcepction("Nao foi encontrado nenhum cliente!");
        return clientes;
    }

    public Cliente fromDTO(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail());
    }


    private void updateData(Cliente newCliente, Cliente cliente){
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }

}
