# relatosdepapel-buscador-ms

## Requisitos para ejecutar el microservicio

1. **Java 21**
   - Necesitas tener instalado openjdk21.
   - Verifica tu versión con:
     ```zsh
     java -version
     ```
   - Si no tienes Java 21, puedes instalarlo con Homebrew:
     ```zsh
     brew install openjdk@21
     ```

2. **Maven**
   - Necesitas Maven para compilar y ejecutar el proyecto. Puedes usar el wrapper incluido (`./mvnw`) o instalar Maven:
     ```zsh
     brew install maven
     ```
   - Verifica Maven con:
     ```zsh
     mvn -version
     ```

3. **Puerto 8080 libre**
   - El microservicio usa el puerto 8080 por defecto. Asegúrate de que esté disponible.

4. **No necesitas base de datos externa**
   - El proyecto usa H2 en memoria, configurado en `src/main/resources/application.properties`.
   - Los scripts `schema.sql` y `data.sql` en `src/main/resources/` crean y pueblan la base de datos automáticamente al iniciar.

5. **Estructura de archivos necesaria**
   - `pom.xml` (gestión de dependencias y plugins)
   - Código fuente en `src/main/java/com/relatosdepapel/relatosdepapel_buscador_ms/`
   - Archivos de configuración y scripts en `src/main/resources/` (`application.properties`, `schema.sql`, `data.sql`)

## Pasos para ejecutar

1. Abre una terminal en la raíz del proyecto.
2. Ejecuta:
   ```zsh
   ./mvnw spring-boot:run
   ```
   o, para empaquetar y correr el JAR:
   ```zsh
   ./mvnw clean package -DskipTests
   java -jar target/relatosdepapel-buscador-ms-*.jar
   ```

3. Accede a la API en:  
   [http://localhost:8080/books](http://localhost:8080/books)

## Ejemplos de uso de la API (curl)

### 1. Obtener todos los libros
**GET** `/books`
```zsh
curl -X GET http://localhost:8080/books
```

### 2. Crear un libro
**POST** `/books`
```zsh
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{
    "image": "https://ejemplo.com/portada.jpg",
    "author": "Autor Nuevo",
    "title": "Libro Nuevo",
    "description": "Descripción del libro.",
    "type": "digital",
    "price": 1500.00,
    "editorial": "Editorial X",
    "idioma": "Español",
    "paginas": 200,
    "publicacion": 2024,
    "formato": "EPUB",
    "categoria": "Novela",
    "valoracion": 4,
    "visible": true
  }'
```

### 3. Modificar completamente un libro
**PUT** `/books/{id}`
```zsh
curl -X PUT http://localhost:8080/books/1 \
  -H "Content-Type: application/json" \
  -d '{
    "image": "https://ejemplo.com/portada2.jpg",
    "author": "Autor Modificado",
    "title": "Libro Modificado",
    "description": "Nueva descripción.",
    "type": "fisico",
    "price": 2000.00,
    "editorial": "Editorial Y",
    "idioma": "Inglés",
    "paginas": 300,
    "publicacion": 2025,
    "formato": "Tapa dura",
    "categoria": "Ensayo",
    "valoracion": 5,
    "visible": false
  }'
```

### 4. Modificar parcialmente un libro
**PATCH** `/books/{id}`
```zsh
curl -X PATCH http://localhost:8080/books/1 \
  -H "Content-Type: application/json" \
  -d '{"price": 1800.00, "visible": true}'
```

### 5. Eliminar un libro
**DELETE** `/books/{id}`
```zsh
curl -X DELETE http://localhost:8080/books/1
```

### 6. Buscar libros por atributos (individual o combinados)
**GET** `/books/search?author=Gabriel%20Garc%C3%ADa%20M%C3%A1rquez&categoria=Novela`
```zsh
curl -G "http://localhost:8080/books/search" \
  --data-urlencode "author=Gabriel García Márquez" \
  --data-urlencode "categoria=Novela"
```

Puedes combinar cualquier parámetro: `author`, `title`, `type`, `editorial`, `idioma`, `categoria`, `valoracion`, `visible`.

---