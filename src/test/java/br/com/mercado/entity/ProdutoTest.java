package br.com.mercado.entity;

import br.com.mercado.model.entity.Categoria;
import br.com.mercado.model.entity.ItemPedido;
import br.com.mercado.model.entity.Produto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
public class ProdutoTest {

    private Produto criarProduto(){
        Produto produto = new Produto();
        produto.setId(1);
        produto.setCodBarras("78923409823");
        produto.setDataValidade("20/05/02");
        produto.setNome("Pao sovado");
        produto.setValorUnitario(9.99);
        return produto;
    }

    @Test
    @DisplayName("deve adicionar uma categoria ao produto")
    public void deveAdicionarCategoriaAoProduto(){
        Produto produto = criarProduto();
        Categoria categoria = new Categoria(1, "Paes");
        produto.getCategorias().add(categoria);

       assertThat(produto.getCategorias())
                .isNotEmpty()
                //nao está vazia
                .hasSize(1)
                //tem o tamanho de 1
                .contains(categoria);
                //e contem a categoria que foi criada
    }

    @Test
    @DisplayName("Nao deve adicionar uma categoria sem nome ao produto")
    public void naoDeveAdicionarCategoriaSemNomeAoProduto(){
        Produto produto = criarProduto();
        Categoria categoria = new Categoria();
        categoria.setNome(null);
        categoria.setId(1);

        Assertions.assertThrows(RuntimeException.class, () -> produto.setCategoria(categoria));
    }

    @Test
    @DisplayName("Deve adicionar um Item Pedido ao produto")
    public void deveAdicionarItemPedidoAoProduto(){
        Produto produto = criarProduto();
        ItemPedido ip = new ItemPedido();
        produto.getItens().add(ip);

        assertThat(produto.getItens())
                .isNotEmpty()
                //nao está vazia
                .hasSize(1)
                //tem o tamanho de 1
                .contains(ip);
        //e contem a categoria que foi criada
    }

}
