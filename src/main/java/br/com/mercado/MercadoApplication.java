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

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@Override
	public void run(String... args) throws Exception {

		categoriaRepository.deleteAll();
		produtoRepository.deleteAll();

		Categoria cat1 = new Categoria(1, "Mercearia");
		Categoria cat2 = new Categoria(2, "Biscoitos");
		Categoria cat3 = new Categoria(3, "Pães");
		Categoria cat4 = new Categoria(4, "Limpeza");

		//PRODUTOS DA MERCEARIA
		Produto p1 = new Produto(1, "Farinha de trigo 1kg", "1046696",
				"20/02/2022", 4.50, Arrays.asList(cat1));
		Produto p2 = new Produto(2, "Feijao 1kg", "9034570",
				"30/07/2022", 6.99, Arrays.asList(cat1));
		Produto p3 = new Produto(3, "Açucar 1kg", "2842679",
				"20/02/2023", 3.79, Arrays.asList(cat1));
		Produto p4 = new Produto(4, "Arroz 1kg", "3977480",
				"20/02/2024", 4.99, Arrays.asList(cat1));
		Produto p5 = new Produto(5, "Arroz 5kg", "8246718",
				"20/02/2022", 23.59, Arrays.asList(cat1));

		//PRODUTOS DO BISCOITO
		Produto p6 = new Produto(6, "Bolacha Maizena", "9128725",
				"30/12/2022", 4.59, Arrays.asList(cat2));
		Produto p7 = new Produto(7, "Bolacha Doce Negresco", "1859624",
				"30/12/2022", 3.60, Arrays.asList(cat2));
		Produto p8 = new Produto(8, "Bolacha Doce Trakinas", "9142836",
				"30/12/2022", 2.50, Arrays.asList(cat2));
		Produto p9 = new Produto(9, "Bolacha agua e sal", "6898232",
				"30/12/2022", 2.99, Arrays.asList(cat2));
		Produto p10 = new Produto(10, "Bolacha cream cracker", "4583079",
				"30/12/2022", 2.99, Arrays.asList(cat2));

		//PRODUTOS PAES
		Produto p11 = new Produto(11, "Pao Pullman", "4690943",
				"30/12/2022",  9.90, Arrays.asList(cat3));
		Produto p12 = new Produto(12, "Pao Pullman integral", "1523096",
				"30/12/2022",  8.79, Arrays.asList(cat3));
		Produto p13 = new Produto(13, "Mussarela 100g", "1980854",
				"30/12/2022",  10.90, Arrays.asList(cat3));
		Produto p14 = new Produto(14, "Presunto 100g", "4101273",
				"30/12/2022",  6.90, Arrays.asList(cat3));
		Produto p15 = new Produto(15, "Mortadela 100g", "8808925",
				"30/12/2022",  5.50, Arrays.asList(cat3));

		//Produtos Limpeza
		Produto p16 = new Produto(16, "Amaciante Ype", "2019707",
				"30/12/2022",  9.90, Arrays.asList(cat4));
		Produto p17 = new Produto(17, "Agua sanitaria", "7328132",
				"30/12/2022",  5.99, Arrays.asList(cat4));
		Produto p18 = new Produto(18, "Detergente", "1495987",
				"30/12/2022",  3.50, Arrays.asList(cat4));
		Produto p19 = new Produto(19, "Esponja (1 unid)", "3238276",
				"30/12/2022",  2.50, Arrays.asList(cat4));
		Produto p20 = new Produto(20, "Sabao em po", "8944285",
				"30/12/2022",  23.90, Arrays.asList(cat4));

		//categoria ja cria os porodutos pois categoria TEM produtos
		categoriaRepository.deleteAll();
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,
				p17,p18,p19,p20));

	}
}
