package br.com.mercado.dto;

import br.com.mercado.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteDTO {

    Integer id;

    @NotEmpty(message = "Preenchimento obrigatorio")
    String nome;

    @NotEmpty(message = "Preenchimento obrigatorio")
    @Email(message = "email Invalido")
    String email;

    public ClienteDTO(Cliente cliente){
        id = cliente.getId();
        nome = cliente.getNome();
        email = cliente.getEmail();
    }
}
