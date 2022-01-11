# demo-market-project
API REST para Mercado

Documentacao da API: <a href="https://demo-market-project.herokuapp.com/swagger-ui/index.html" target="_blank">Swagger UI</a> 


Postman: <a href="https://documenter.getpostman.com/view/17639626/UVXgLHQS" target="_blank">Swagger UI</a> 

-------

<h1>Tecnologias</h1>

<ul>
<li>Java</li>
<li>Spring Data</li>
<li>Spring Security</li>
<li>MySQL</li>
</ul>

-------

<h1>Descrição Geral</h1>

<p>API REST para a utilização de mercado. um funcionário do mercado que fica no caixa, irá consumir a API de pedidos, onde você cria um pedido (com cliente ou sem clinete, perguntar o cpf do cliente e adicionar esse cliente ao pedido pode trazer um desconto), adiciona os produtos ao pedido, passando a quantidade e o código de barras do pedido, após isso, poderá colocar a forma de pagamento de certo pedido e alterar o status do pedido (Em Andamento, Cancelado ou Finalizado).</p>

<p>Poderemos consumir a API pela gerência do mercado tambem, podendo manipular os produtos, categorias e os clientes que estão na base de dados.</p>

-------

<h1>Requisitos</h1>
<p>O projeto foi realizado e manejado por meio de Sprints e a cada semana, uma Sprint nova (nos issues está todas as Sprint desde a Sprint 1)</p>






-------
<h1>Modelagem de classe:</h1>
![image](https://user-images.githubusercontent.com/75227855/148631571-3d1e4c26-fc46-4e01-bec0-7051bed9d53c.png)
