package br.com.mercado.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public interface BDService {

    @Bean
    public void iniciarBanco();
}
