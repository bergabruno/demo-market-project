package br.com.mercado.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class StandardError {

    private Long timestamp;
    private Integer status;
    private String message;
    private String path;


}
