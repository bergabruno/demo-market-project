package br.com.mercado;

import br.com.mercado.model.entity.*;
import br.com.mercado.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
@RestController
@AllArgsConstructor
public class MercadoApplication implements CommandLineRunner {

	private ClienteRepository clienteRepository;

	private PedidoRepository pedidoRepository;

	CategoriaRepository categoriaRepository;

	ProdutoRepository produtoRepository;

	EstoqueRepository estoqueRepository;


	public static void main(String[] args) {
		SpringApplication.run(MercadoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Cliente cliente = new Cliente();
		Categoria cat1 = new Categoria(null, "Informatica");
		Produto p1 = new Produto(null, "Computador");
		Produto p2 = new Produto(null, "Notebook");

		cat1.getProdutos().addAll(Arrays.asList(p1,p2));

		Pedido ped1 = new Pedido(null, cliente);
		ped1.getProdutos().addAll(Arrays.asList(p1,p2));
		p1.getPedidos().add(ped1);
		p2.getPedidos().add(ped1);


		cliente.getPedidos().add(ped1);

		clienteRepository.save(cliente);


	}
}
