CREATE TABLE produtos(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome VARCHAR(180) UNIQUE NOT NULL,
    descricao VARCHAR(250),
    categoria_do_produto VARCHAR(150) NOT NULL,
    FOREIGN KEY (categoria_do_produto) REFERENCES categorias(categoria)
)