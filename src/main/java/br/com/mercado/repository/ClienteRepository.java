package br.com.mercado.repository;

import br.com.mercado.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {

    public Optional<Cliente> findByCpf(String cpf);

    public boolean existsByEmail(String email);

    public boolean existsByCpf(String cpf);

}
