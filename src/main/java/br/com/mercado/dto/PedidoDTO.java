package br.com.mercado.dto;

import br.com.mercado.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoDTO {

    private Integer id;

    private Cliente cliente;

    private Integer statusPedido;

}
