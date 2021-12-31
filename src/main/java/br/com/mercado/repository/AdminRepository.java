package br.com.mercado.repository;

import br.com.mercado.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    public Optional<Admin> findByLogin(String login);

    public boolean existsByLogin(String login);

}
