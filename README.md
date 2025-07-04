# relatosdepapel-buscador-ms

## Requisitos para ejecutar el microservicio

1. **Java 21**
   - Necesitas tener instalado openjdk21.

   - Si no tienes Java 21, puedes instalarlo con Homebrew:
     ```zsh
     brew install openjdk@21
     ```
    - Ejecuta:
    ```zsh
    export JAVA_HOME="/opt/homebrew/opt/openjdk@21"
    export PATH="$JAVA_HOME/bin:$PATH"
    ```
    - Verifica tu versión con: debe ser la 21
     ```zsh
     java -version
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
 ### 3. Eliminar un libro
**DELETE** `/books/{id}`
```zsh
curl -X DELETE http://localhost:8080/books/1
```

### 4. Buscar libros por atributos (individual o combinados)
**GET** `/books?author=Gabriel%20Garc%C3%ADa%20M%C3%A1rquez&categoria=Novela`
```zsh
curl -G "http://localhost:8080/books/search" \
  --data-urlencode "author=Gabriel García Márquez" \
  --data-urlencode "categoria=Novela"
```

### 4. Buscar libros id
**GET** `/books/Bwpt05cBTo8i77nQmJq4`
```zsh
curl -G "http://localhost:8080/books/Bwpt05cBTo8i77nQmJq4"  
```

Puedes combinar cualquier parámetro: `author`, `title`, `type`, `categoria`, `active`.


## Ejecución alternativa solo con Podman (sin Maven ni Java local)

Si no quieres instalar Maven ni compilar en local, puedes construir y ejecutar el microservicio directamente con Podman. El Dockerfile está preparado para compilar el proyecto dentro del contenedor usando una build multietapa.

### Requisito:
- Debes tener instalado **Podman** en tu sistema.

### Pasos:

1. Abre una terminal en la raíz del proyecto.
2. Ejecuta:
   ```zsh
   ./build-and-run.sh
   ```

Este script construirá la imagen y ejecutará el microservicio dentro de un contenedor Podman, sin requerir Maven ni Java instalados en tu máquina local.

## Docker
### Construir la imagen
- `docker build -t relatosdepapel-buscador-ms .`
### Obtener IP de Eureka
- Para obtener la IP de Eureka debemos ejecutar el siguiente comando, donde `<<container-id>>` lo podemos obtener ejecutando `docker ps`.
  ```
  docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <<container-id>>
  ```
### Ejecutar el microservicio usando Docker
- Para ejecutar el microservicio ejecutamos el siguiente comando, donde `<<IP_EUREKA>>` es la IP obtenida anteriormente.
  ```
  docker run -p 8080:8080 -e EUREKA_URL=http://<<IP_EUREKA>>:8761/eureka relatosdepapel-buscador-ms
  ```