package br.com.mercado.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<MensagemException.Campo> campos = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            campos.add(new MensagemException.Campo(nome, mensagem));
        }

        MensagemException mensagemException = new MensagemException();
        mensagemException.setStatus(status.value());
        mensagemException.setDataHora(OffsetDateTime.now());
        mensagemException.setTitulo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente");
        mensagemException.setCampos(campos);
        return handleExceptionInternal(ex, mensagemException, headers, status, request);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleNegocio(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        MensagemException mensagemException = new MensagemException();
        mensagemException.setStatus(status.value());
        mensagemException.setDataHora(OffsetDateTime.now());
        mensagemException.setTitulo(ex.getMessage());
        return handleExceptionInternal(ex, mensagemException, new HttpHeaders(), status, request);
    }
}
