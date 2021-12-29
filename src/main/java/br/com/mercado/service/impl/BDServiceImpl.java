package br.com.mercado.service.impl;

import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.Produto;
import br.com.mercado.repository.CategoriaRepository;
import br.com.mercado.repository.ClienteRepository;
import br.com.mercado.repository.PedidoRepository;
import br.com.mercado.repository.ProdutoRepository;
import br.com.mercado.service.BDService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@AllArgsConstructor
@Service
public class BDServiceImpl implements BDService {

    CategoriaRepository categoriaRepository;

    ProdutoRepository produtoRepository;

    public void iniciarBanco(){
        Categoria cat1 = new Categoria(1, "Mercearia");
        Categoria cat2 = new Categoria(2, "Biscoitos");
        Categoria cat3 = new Categoria(3, "Pães");
        Categoria cat4 = new Categoria(4, "Limpeza");

        //PRODUTOS DA MERCEARIA
        Produto p1 = new Produto(1, "Farinha de trigo 1kg", "1046696",
                "20/02/2022", 4.50);
        Produto p2 = new Produto(2, "Feijao 1kg", "9034570",
                "30/07/2022", 6.99);
        Produto p3 = new Produto(3, "Açucar 1kg", "2842679",
                "20/02/2023", 3.79);
        Produto p4 = new Produto(4, "Arroz 1kg", "3977480",
                "20/02/2024", 4.99);
        Produto p5 = new Produto(5, "Arroz 5kg", "8246718",
                "20/02/2022", 23.59);
        p1.setCategoria(cat1);
        p2.setCategoria(cat1);
        p3.setCategoria(cat1);
        p4.setCategoria(cat1);
        p5.setCategoria(cat1);

        cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3,p4,p5));

        //PRODUTOS DO BISCOITO
        Produto p6 = new Produto(6, "Bolacha Maizena", "9128725",
                "30/12/2022", 4.59);
        Produto p7 = new Produto(7, "Bolacha Doce Negresco", "1859624",
                "30/12/2022", 3.60);
        Produto p8 = new Produto(8, "Bolacha Doce Trakinas", "9142836",
                "30/12/2022", 2.50);
        Produto p9 = new Produto(9, "Bolacha agua e sal", "6898232",
                "30/12/2022", 2.99);
        Produto p10 = new Produto(10, "Bolacha cream cracker", "4583079",
                "30/12/2022", 2.99);

        p6.setCategoria(cat2);
        p7.setCategoria(cat2);
        p8.setCategoria(cat2);
        p9.setCategoria(cat2);
        p10.setCategoria(cat2);

        cat2.getProdutos().addAll(Arrays.asList(p6,p7,p8,p9,p10));

        //PRODUTOS PAES
        Produto p11 = new Produto(11, "Pao Pullman", "4690943",
                "30/12/2022",  9.90);
        Produto p12 = new Produto(12, "Pao Pullman integral", "1523096",
                "30/12/2022",  8.79);
        Produto p13 = new Produto(13, "Mussarela 100g", "1980854",
                "30/12/2022",  10.90);
        Produto p14 = new Produto(14, "Presunto 100g", "4101273",
                "30/12/2022",  6.90);
        Produto p15 = new Produto(15, "Mortadela 100g", "8808925",
                "30/12/2022",  5.50);

        p11.setCategoria(cat3);
        p12.setCategoria(cat3);
        p13.setCategoria(cat3);
        p14.setCategoria(cat3);
        p15.setCategoria(cat3);
        cat3.getProdutos().addAll(Arrays.asList(p11,p12,p13,p14,p15));

        //Produtos Limpeza
        Produto p16 = new Produto(16, "Amaciante Ype", "2019707",
                "30/12/2022",  9.90);
        Produto p17 = new Produto(17, "Agua sanitaria", "7328132",
                "30/12/2022",  5.99);
        Produto p18 = new Produto(18, "Detergente", "1495987",
                "30/12/2022",  3.50);
        Produto p19 = new Produto(19, "Esponja (1 unid)", "3238276",
                "30/12/2022",  2.50);
        Produto p20 = new Produto(20, "Sabao em po", "8944285",
                "30/12/2022",  23.90);

        p16.setCategoria(cat4);
        p17.setCategoria(cat4);
        p18.setCategoria(cat4);
        p19.setCategoria(cat4);
        p20.setCategoria(cat4);
        cat4.getProdutos().addAll(Arrays.asList(p16,p17,p18,p19,p20));

//		Pedido ped1 = new Pedido(null, cliente, StatusPedido.FINALIZADO, LocalDate.now());
//
//		Pedido ped2 = new Pedido(null, cliente, StatusPedido.EM_ANDAMENTO,LocalDate.now());
//
//		ItemPedido ip1 = new ItemPedido(p1, ped1, 0.0, 2,p1.getValorUnitario() );
//		ItemPedido ip2 = new ItemPedido(p2, ped1, 0.0, 1, p2.getValorUnitario());
//		ItemPedido ip3 = new ItemPedido(p3, ped1, 0.0, 1, p3.getValorUnitario());
//
//		ItemPedido ip4 = new ItemPedido(p2, ped2, 0.0, 5, p2.getValorUnitario());


//		ped1.getItens().addAll(Arrays.asList(ip1,ip2,ip3));
//		ped2.getItens().add(ip4);
//
//		p1.getItens().addAll(Arrays.asList(ip1));
//		p2.getItens().addAll(Arrays.asList(ip3,ip4));
//		p3.getItens().addAll(Arrays.asList(ip2));
//
//
//		cliente.getPedidos().addAll(Arrays.asList(ped1,ped2));
//		clienteRepository.save(cliente);

        //categoria ja cria os porodutos pois categoria TEM produtos
        categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4));
    }
}
