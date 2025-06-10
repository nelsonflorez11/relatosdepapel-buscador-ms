#!/bin/zsh

# Construir la imagen Docker (compila y empaqueta el JAR dentro del contenedor)
podman build -t relatosdepapel-buscador-ms .

# Ejecutar el contenedor
podman run -p 8080:8080 relatosdepapel-buscador-ms
