package br.com.mercado;

import br.com.mercado.model.entity.*;
import br.com.mercado.model.entity.enums.StatusPedido;
import br.com.mercado.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
@RestController
public class MercadoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MercadoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
