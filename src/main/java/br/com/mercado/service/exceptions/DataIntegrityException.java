package br.com.mercado.service.exceptions;

public class DataIntegrityException extends  RuntimeException{

    public DataIntegrityException(String msg){
        super(msg);
    }


}
