CREATE TABLE movimentacoes(
    id INT PRIMARY KEY AUTO_INCREMENT,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL,
    data DATE NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
)