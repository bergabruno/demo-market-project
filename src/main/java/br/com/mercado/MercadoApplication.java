package br.com.mercado;

import br.com.mercado.model.entity.*;
import br.com.mercado.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
@RestController
@AllArgsConstructor
public class MercadoApplication implements CommandLineRunner {

	private ClienteRepository clienteRepository;

	private PedidoRepository pedidoRepository;

	CategoriaRepository categoriaRepository;

	ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(MercadoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Cliente cliente = new Cliente(null, "Bruno Bergamasco", "322131212");
		Categoria cat1 = new Categoria(null, "Informatica");


		Produto p1 = new Produto(null, "Computador", "645098349085",
				"20/02/2002", 2, 10.00);
		Produto p2 = new Produto(null, "Notebook", "05478305834",
				"30/12/2021", 10, 25.00);
		Produto p3 = new Produto(null, "Testancia", "05478305834",
				"30/12/2021", 10, 50.00);

		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		p1.setCategoria(cat1);
		p2.setCategoria(cat1);
		p3.setCategoria(cat1);

		Pedido ped1 = new Pedido(null, cliente);

		Pedido ped2 = new Pedido(null, cliente);
		
		ped1.setValorTotal(ped1.getValorTotal());



		ItemPedido ip1 = new ItemPedido(p1, ped1, 0.0, 2);
		ItemPedido ip2 = new ItemPedido(p2, ped1, 0.0, 1);
		ItemPedido ip3 = new ItemPedido(p3, ped1, 0.0, 1);


		ped1.getItemPedidos().addAll(Arrays.asList(ip1,ip2,ip3));

		p1.getItemPedidos().addAll(Arrays.asList(ip1));
		p2.getItemPedidos().addAll(Arrays.asList(ip3));
		p3.getItemPedidos().addAll(Arrays.asList(ip2));



		cliente.getPedidos().addAll(Arrays.asList(ped1,ped2));
		clienteRepository.save(cliente);

//		System.out.println(ped2.getValorTotal());

	}
}
