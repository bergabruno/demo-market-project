package br.com.mercado.service;

import br.com.mercado.service.exceptions.ObjectNotFoundExcepction;
import br.com.mercado.model.entity.Cliente;
import br.com.mercado.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public Cliente buscar(Integer id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundExcepction("Erro ao encontrar por este codigo!"));
    }
}
