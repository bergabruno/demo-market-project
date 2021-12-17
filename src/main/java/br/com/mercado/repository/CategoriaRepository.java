package br.com.mercado.repository;

import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {
}
