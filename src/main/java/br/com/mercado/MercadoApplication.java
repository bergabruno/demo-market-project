package br.com.mercado;

import br.com.mercado.model.entity.*;
import br.com.mercado.model.entity.enums.StatusPedido;
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

	public static void main(String[] args) {
		SpringApplication.run(MercadoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Cliente cliente = new Cliente(null, "Bruno Bergamasco", "brunoberga@gmail.com", "543534543");

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");


		Produto p1 = new Produto(null, "Computador", "645098349085",
				"20/02/2002", 10.00);
		Produto p2 = new Produto(null, "Notebook", "430345874524",
				"30/12/2021", 25.00);
		Produto p3 = new Produto(null, "Farinha de trigo", "05478305834",
				"30/12/2021",  4.50);

		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		p1.setCategoria(cat1);
		p2.setCategoria(cat1);
		p3.setCategoria(cat1);

		Pedido ped1 = new Pedido(null, cliente, StatusPedido.FINALIZADO);

		Pedido ped2 = new Pedido(null, cliente, StatusPedido.EM_ANDAMENTO);

		ItemPedido ip1 = new ItemPedido(p1, ped1, 0.0, 2,p1.getValorUnitario() );
		ItemPedido ip2 = new ItemPedido(p2, ped1, 0.0, 1, p2.getValorUnitario());
		ItemPedido ip3 = new ItemPedido(p3, ped1, 0.0, 1, p3.getValorUnitario());

		ItemPedido ip4 = new ItemPedido(p2, ped2, 0.0, 5, p2.getValorUnitario());


		ped1.getItens().addAll(Arrays.asList(ip1,ip2,ip3));
		ped2.getItens().add(ip4);

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3,ip4));
		p3.getItens().addAll(Arrays.asList(ip2));


		cliente.getPedidos().addAll(Arrays.asList(ped1,ped2));
		clienteRepository.save(cliente);

		categoriaRepository.saveAll(Arrays.asList(cat2,cat3,cat4,cat5,cat6,cat7));

	}
}
