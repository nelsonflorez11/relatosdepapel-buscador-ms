#!/bin/zsh


podman build -t relatosdepapel-buscador-ms .


podman run -p 8080:8080 relatosdepapel-buscador-ms
