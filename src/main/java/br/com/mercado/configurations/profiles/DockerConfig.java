package br.com.mercado.configurations.profiles;

import br.com.mercado.service.BDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("docker")
public class DockerConfig {
    //O spring entende que ele tem que rodar por conta que o profile test está ativo,
    //@Configuration - todos os @Bean da classe serao iniciados quando o profile teste estiver rodando.

    @Autowired
    BDService bdService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Bean
    public boolean instanciarBanco(){
        if("create".equalsIgnoreCase(ddl)){
            bdService.iniciarBanco();
            return true;
        }
        return false;
    }
}
