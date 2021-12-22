package br.com.mercado.controller.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter
public class ValidationError extends StandardError{

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Long timestamp, Integer status, String message, String path) {
        super(timestamp, status, message, path);
    }


    public void addError(String fieldname, String msg){
        errors.add(new FieldMessage(fieldname, msg));
    }
}
