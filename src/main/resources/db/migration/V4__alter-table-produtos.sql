ALTER TABLE produtos
ADD COLUMN categoria_id INT,
ADD CONSTRAINT fk_produtos_categorias
FOREIGN KEY (categoria_id) REFERENCES categorias(id);
