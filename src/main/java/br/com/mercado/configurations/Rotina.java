package br.com.mercado.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Rotina {

    @Scheduled(cron = "1 35 17 20 1 ?", zone = "America/Sao_Paulo")
    public void teste(){
        System.out.println("WORKING");
    }
}
