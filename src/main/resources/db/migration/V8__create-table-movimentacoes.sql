CREATE TABLE movimentacoes(
    id INT PRIMARY KEY AUTO_INCREMENT,
    produto_id INT,
    quantidade INT,
    data DATE,
    tipo VARCHAR(30),
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
)