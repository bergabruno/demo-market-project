package br.com.mercado.repository;

import br.com.mercado.model.entity.Cliente;
import br.com.mercado.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Integer> {


    public Optional<Produto> findByCodBarras(String codBarras);

    public boolean existsByCodBarras(String codBarras);
}
