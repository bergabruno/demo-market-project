package br.com.mercado.dto;

import br.com.mercado.model.entity.Categoria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter@Setter
public class CategoriaDTO {

    private Integer id;

    private String nome;

    public CategoriaDTO(Categoria categoria){
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }
}
