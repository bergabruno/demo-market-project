package br.com.mercado;

import br.com.mercado.model.entity.Pedido;
import br.com.mercado.service.PedidoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SpringBootApplication
@RestController
@Log4j2
public class MercadoApplication implements CommandLineRunner{

    @Autowired
    PedidoService pedidoService;

    public static void main(String[] args) {
        SpringApplication.run(MercadoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        log.info("Produto mais visto no pedido 1:");
//
//        Pedido pedidos = pedidoService.buscarPorCodigo(2);
//        String nome = null;
//
//        for(int i = 0; i < pedidos.getItens().size() -1 ; i++){
//            if(pedidos.getItens().get(i).getQuantidade() > pedidos.getItens().get(i+1).getQuantidade()){
//                nome = pedidos.getItens().get(i).getProduto().getNome();
//            }else{
//                nome = pedidos.getItens().get(i+1).getProduto().getNome();
//            }
//        }
//
//        System.out.println(nome);

    }
}

