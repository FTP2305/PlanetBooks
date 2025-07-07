DROP DATABASE planet_books_db;
CREATE DATABASE planet_books_db;
USE planet_books_db;

-- 1. Tabla Autores
CREATE TABLE autores (
    id_autor INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(200) NOT NULL
);

-- 2. Tabla Editoriales
CREATE TABLE editoriales (
    id_editorial INT AUTO_INCREMENT PRIMARY KEY,
    nombre_editorial VARCHAR(200) NOT NULL UNIQUE
);

-- 3. Tabla Categorias (con soporte para jerarquía tipo árbol)
CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(100) NOT NULL UNIQUE,
    id_categoria_padre INT,
    FOREIGN KEY (id_categoria_padre) REFERENCES categorias(id_categoria) ON DELETE SET NULL
);

-- 4. Tabla Libros
CREATE TABLE libros (
    isbn VARCHAR(13) PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    id_autor_principal INT,    -- Cambiado a INT simple, sin NOT NULL ni UNSIGNED
    id_editorial INT,          -- Cambiado a INT simple, sin NOT NULL ni UNSIGNED
    anio_publicacion YEAR,
    stock_total INT,
    stock_disponible INT
);

-- 5. Tabla de Unión Libros_Categorias (para relación N:M entre libros y categorías)
CREATE TABLE libros_categorias (
    isbn_libro VARCHAR(13) NOT NULL,
    id_categoria INT NOT NULL,
    PRIMARY KEY (isbn_libro, id_categoria),
    FOREIGN KEY (isbn_libro) REFERENCES libros(isbn) ON DELETE CASCADE,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria) ON DELETE CASCADE
);

-- 6. Tabla Usuarios
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE
);

-- 7. Tabla Prestamos
CREATE TABLE prestamos (
    id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    isbn_libro VARCHAR(13) NOT NULL,
    fecha_prestamo DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_devolucion_esperada DATETIME NOT NULL,
    fecha_devolucion_real DATETIME,
    estado_prestamo ENUM('ACTIVO', 'DEVUELTO', 'ATRASADO', 'CANCELADO') NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (isbn_libro) REFERENCES libros(isbn) 
);

-- 8. Tabla Recomendaciones
CREATE TABLE recomendaciones (
    id_recomendacion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT  ,
    isbn_libro_recomendado VARCHAR(13) NOT NULL,
    fecha_generacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (isbn_libro_recomendado) REFERENCES libros(isbn) ON DELETE CASCADE,
    UNIQUE (id_usuario, isbn_libro_recomendado)
);

INSERT INTO autores (id_autor, nombre_completo) VALUES (1, 'Stephen King');
INSERT INTO editoriales (id_editorial, nombre_editorial) VALUES (1, 'Editorial Planeta');