````markdown
# Inventory Stock Manager

Restful API for creating and managing categories, products, and movements.

[Portuguese Doc](https://github.com/Viinicius-Muller/inventory-stock-manager/blob/main/README.md) 

## Features

- Create and deactivate categories
- Create and deactivate products
- Automatic creation and reading of movements
- Define prices, quantities, and minimum stock
- Documentation via Swagger-UI


### Technologies Used
- **Backend**
  - [Java](https://www.java.com/en/)
  - [Spring Boot](https://spring.io/projects/spring-boot)
  - [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
  - [Bean Validation](https://beanvalidation.org/)
  - [Lombok](https://projectlombok.org/)
  - [OpenAPI 3](https://springdoc.org/)
- **Database**
  - [MySQL](https://www.mysql.com/)
  - [Flyway](https://www.red-gate.com/products/flyway/community/)
- **Tools**
  - [Maven](https://maven.apache.org/)
  - [Postman](https://www.postman.com/)
  - [Swagger UI](https://swagger.io/tools/swagger-ui/)

## Instructions

### Prerequisites
- **[Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/#java21) - Version 21 or higher**
```bash
# Ubuntu
sudo apt-get install openjdk-21-jdk
````

- **[Maven](https://maven.apache.org/download.cgi)**

<!-- end list -->

```bash
# Ubuntu
sudo apt-get install maven
```

- **[MySQL Server](https://dev.mysql.com/downloads/installer/) - Database Server**

<!-- end list -->

```bash
# Ubuntu
sudo sudo apt install mysql-server
```

### Installation

1.  **Clone the project**

<!-- end list -->

```bash
git clone [https://github.com/Viinicius-Muller/inventory-stock-manager.git](https://github.com/Viinicius-Muller/inventory-stock-manager.git)
```

2.  **Open the project in a Java IDE**
3.  **Install the Maven dependencies**

<!-- end list -->

```bash
mvn install
```

### Database Setup

1.  **Create a new database in MySQL**

<!-- end list -->

- Use the command `CREATE DATABASE database_name`

<!-- end list -->

2.  **Configure the database connection**

<!-- end list -->

- Open the file `src/main/resources/application.properties`
- Configure the following settings with your MySQL server data:

<!-- end list -->

```bash
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3.  **Run the InventoryStockManagerApplication application**

<!-- end list -->

- The database tables will be automatically created by the Flyway migrations
- Access the [Swagger UI](https://www.google.com/search?q=http://localhost:8080/swagger-ui/index.html%23/) page for documentation and to test the requests

## Usage

To run the application, connect the Database server and run the Spring application via your IDE, or use the Maven command in the root folder:

```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

## API Endpoints

The Swagger UI documentation will be available at `http://localhost:8080/swagger-ui/index.html#/` when running the application.

### Product Controller

- **POST `/produtos`**

    - Registers a new product in the database
    - POST Example:

  <!-- end list -->

  ```json
    {
        "nome": "Steel Wool",
        "descricao": "optional description",
        "categoria": "Cleaning",
        "preco_atual": 4.55,
        "estoque_atual": 25,
        "estoque_minimo": 10
    }
  ```

  **Optional Fields**: estoque\_atual, estoque\_minimo, preco\_atual, descricao.

- **GET `/produtos`**

    - Returns a list of all active products that have an active category
      **Parameters:**
        - ativo - Returns products by their attribute (active)
        - categoria - Returns all products of a certain category
    - GET Example: `http://localhost:8080/produtos?ativo=true&categoria=cleaning`

- **GET `/produtos/{id}`**

    - Returns information for a product by its ID

- **PATCH `/produtos/{id}`**

    - Updates a product's attributes
    - PATCH Example:

  <!-- end list -->

  ```json
    {
        "nome": "New name",
        "descricao": "New description",
        "preco_atual": 40,
        "estoque_atual": 25,
        "estoque_minimo": 10
    }
  ```

  **Updatable fields:** nome, descricao, preco\_atual, estoque\_atual, estoque\_minimo.

- **PATCH `/produtos/{id}/reativar`**

    - Changes the 'active' attribute of a product to true

- **DELETE `/produtos/{id}`**

    - Changes the 'active' attribute of a product to false

### Category Controller

- **POST `/categorias`**

    - Registers a new category in the database
    - POST Example:

  <!-- end list -->

  ```json
    {
        "nome": "Cleaning"
    }
  ```

- **GET `/categorias`**

    - Returns a list of all active categories
      **Parameters:**
        - ativo - Returns categories by their attribute (active)
    - GET Example: `http://localhost:8080/categorias?ativo=true`

- **PATCH `/categorias/{id}`**

    - Updates a category's name
    - PATCH Example:

  <!-- end list -->

  ```json
    {
        "nome": "New name",
    }
  ```

- **PATCH `/categorias/{id}/reativar`**

    - Changes the 'active' attribute of a category to true

- **DELETE `/categorias/{id}`**

    - Changes the 'active' attribute of a category to false

### Movements Controller

The creation of **movements** occurs automatically, caused by the creation of a new product via **POST**, or by changing its quantity via **PATCH**.

- **GET `/movimentacoes`**
    - Returns a list of all movements
      **Parameters:**
        - produtoId - Returns all movements of a product by its ID
    - GET Example: `http://localhost:8080/movimentacoes?produtoId=1`

## Contributions

#### Bug Reports and Contributions

To contribute to this project, use the dedicated [page](https://github.com/Viinicius-Muller/inventory-stock-manager/issues).

### Contact

André Vinicius Müller | [Linkedin](www.linkedin.com/in/andré-vinicius-muller-432b17327) | zandreviniciusmuller@gmail.com