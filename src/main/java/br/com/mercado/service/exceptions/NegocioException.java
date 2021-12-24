package br.com.mercado.service.exceptions;

public class NegocioException extends RuntimeException{

    public NegocioException(String msg){
        super(msg);
    }
}
