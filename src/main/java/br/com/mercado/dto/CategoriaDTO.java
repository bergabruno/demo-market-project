package br.com.mercado.dto;

import br.com.mercado.model.entity.Categoria;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter@Setter
public class CategoriaDTO {

    private Integer id;

    @NotEmpty(message = "Preenchimento Obrigatorio")
    @Length(min = 3, max = 80, message = "nome precisa estar entre 3-80")
    private String nome;

    public CategoriaDTO(Categoria categoria){
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }
}
