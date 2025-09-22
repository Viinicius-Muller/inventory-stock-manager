
# Inventory Stock Manager

Restful API para a criação e gerenciamento de categorias, produtos e movimentações.

[English Doc](https://github.com/Viinicius-Muller/inventory-stock-manager/blob/main/README-en.md)
## Features

- Criação e desativação de categorias
- Criação e desativação de produtos
- Criação automática e leitura de movimentações
- Definições de preços, quantidades e estoque minimo
- Documentação via Swagger-UI


### Tecnologias Utilizadas
- **Backend**
    - [Java](https://www.java.com/en/)
    - [Spring Boot](https://spring.io/projects/spring-boot)
    - [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
    - [Bean Validation](https://beanvalidation.org/)
    - [Lombok](https://projectlombok.org/)
    - [OpenAPI 3](https://springdoc.org/)
- **Banco de Dados**
    - [MySQL](https://www.mysql.com/)
    - [Flyway](https://www.red-gate.com/products/flyway/community/)
- **Ferramentas**
    - [Maven](https://maven.apache.org/)
    - [Postman](https://www.postman.com/)
    - [Swagger UI](https://swagger.io/tools/swagger-ui/)

## Instruções

### Pre requisitos
- **[Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/#java21) - Versão 21 ou superior**
```bash
# Ubuntu
sudo apt-get install openjdk-21-jdk
```
- **[Maven](https://maven.apache.org/download.cgi)**
```bash
# Ubuntu
sudo apt-get install maven
```
- **[MySQL Server](https://dev.mysql.com/downloads/installer/) - Servidor Banco de Dados**
```bash
# Ubuntu
sudo sudo apt install mysql-server
```

### Instalação

1. **Clone o projeto**

```bash
git clone https://github.com/Viinicius-Muller/inventory-stock-manager.git
```
2. **Abra o projeto em alguma IDE Java**
3. **Instale as dependências Maven**

```bash
mvn install
```

### Setup do Banco de Dados
1. **Crie um novo banco de dados no MySQL**
- Utilize o comando `CREATE DATABASE nome_banco_de_dados`
2. **Configure a conexão do banco de dados**
- Abra o arquivo `src/main/resources/application.properties`
- Configure as seguintes configurações com os dados do seu servidor MySQL:
```bash
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/nome_banco_de_dados
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```
3. **Execute a aplicação InventoryStockManagerApplication**
- As tabelas do banco de dados serão automaticamente criadas pelas migrations do Flyway
- Acesse a página do [Swagger UI](http://localhost:8080/swagger-ui/index.html#/) para uma documentação e teste das requisições
## Uso
Para rodar a aplicação, conecte o servidor do Banco de Dados e execute a aplicação Spring via IDE, ou utilize o comando do Maven na pasta raiz:
``` bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.



## Endpoints da API
A documentação Swagger UI estará disponível em `http://localhost:8080/swagger-ui/index.html#/` ao rodar o aplicativo.

### Produto Controller
- **POST `/produtos`**
    - Cadastra um novo produto no banco de dados
    - Exemplo de POST:
  ```json
    {
        "nome": "Bombril",
        "descricao": "descrição opcional",
        "categoria": "Limpeza",
        "preco_atual": 4.55,
        "estoque_atual": 25,
        "estoque_minimo": 10
    }
  ```
  **Campos Opcionais**: estoque_atual, estoque_minimo, preco_atual, descricao.

- **GET `/produtos`**
    - Retorna uma lista de todos os produtos ativos e que contêm categoria ativa
      **Parâmetros:**
        - ativo - Retorna os produtos pelo seu atributo (ativo)
        - categoria - Retorna todos os produtos de certa categoria
    - Exemplo de GET: `http://localhost:8080/produtos?ativo=true&categoria=limpeza`

- **GET `/produtos/{id}`**
    - Retorna as informações de um produto pelo seu Id

- **PATCH `/produtos/{id}`**
    - Atualiza atributos de um produto
    - Exemplo de PATCH:
  ```json
    {
        "nome": "Novo nome",
        "descricao": "Nova descricao",
        "preco_atual": 40,
        "estoque_atual": 25,
        "estoque_minimo": 10
    }
  ```
  **Campos atualizáveis:** nome, descricao, preco_atual, estoque_atual, estoque_minimo.

- **PATCH `/produtos/{id}/reativar`**
    - Altera o atributo 'ativo' de um produto para true

- **DELETE `/produtos/{id}`**
    - Altera o atributo 'ativo' de um produto para false

### Categoria Controller
- **POST `/categorias`**
    - Cadastra uma nova categoria no banco de dados
    - Exemplo de POST:
  ```json
    {
        "nome": "Limpeza"
    }
  ```

- **GET `/categorias`**
    - Retorna uma lista de todas as categorias ativas
      **Parâmetros:**
        - ativo - Retorna as categorias pelo seu atributo (ativo)
    - Exemplo de GET: `http://localhost:8080/categorias?ativo=true`

- **PATCH `/categorias/{id}`**
    - Atualiza o nome de uma categoria
    - Exemplo de PATCH:
  ```json
    {
        "nome": "Novo nome",
    }
  ```

- **PATCH `/categorias/{id}/reativar`**
    - Altera o atributo 'ativo' de uma categoria para true

- **DELETE `/categorias/{id}`**
    - Altera o atributo 'ativo' de uma categoria para false

### Movimentacoes Controller
A criação de **movimentações** ocorre de forma automática, causada pela criação de um novo produto via **POST**, ou pela alteração de sua quantidade via **PATCH**.
- **GET `/movimentacoes`**
    - Retorna uma lista de todas as movimentações
      **Parâmetros:**
        - produtoId - Retorna todas as movimentações de um produto pelo seu Id
    - Exemplo de GET: `http://localhost:8080/movimentacoes?produtoId=1`

## Contribuições
#### Reportes de Bug e Contribuições
Para contribuir com esse projeto utilize a [página](https://github.com/Viinicius-Muller/inventory-stock-manager/issues) dedicada.

### Contato
André Vinicius Müller | [Linkedin](www.linkedin.com/in/andré-vinicius-muller-432b17327) | zandreviniciusmuller@gmail.com
Lucas
