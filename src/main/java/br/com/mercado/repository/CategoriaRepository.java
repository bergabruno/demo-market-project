package br.com.mercado.repository;

import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {

    public boolean existsByNome(String nome);
}
