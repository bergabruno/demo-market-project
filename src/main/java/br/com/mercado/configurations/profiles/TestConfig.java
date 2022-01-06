package br.com.mercado.configurations.profiles;

import br.com.mercado.service.BDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {
    //O spring entende que ele tem que rodar por conta que o profile test está ativo,
    //@Configuration - todos os @Bean da classe serao iniciados quando o profile teste estiver rodando.

    @Autowired
    BDService bdService;

//    @Bean
//    public boolean instanciarBanco(){
//        bdService.iniciarBanco();
//        return true;
//    }
}
