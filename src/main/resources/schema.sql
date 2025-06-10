CREATE TABLE book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    image VARCHAR(1000),
    author VARCHAR(255),
    title VARCHAR(255),
    description TEXT,
    type VARCHAR(50),
    price DECIMAL(10,2),
    editorial VARCHAR(255),
    idioma VARCHAR(100),
    paginas INT,
    publicacion INT,
    formato VARCHAR(100)
);