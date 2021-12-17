package br.com.mercado.controller;

import br.com.mercado.model.entity.Categoria;
import br.com.mercado.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    CategoriaRepository categoriaRepository;


    @GetMapping("/buscar/{id}")
    public ResponseEntity<Categoria> buscar(@PathVariable Integer id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        return new ResponseEntity<Categoria>(categoria.get(), HttpStatus.OK);
    }
}
