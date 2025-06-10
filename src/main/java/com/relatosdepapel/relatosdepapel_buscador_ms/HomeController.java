package com.relatosdepapel.relatosdepapel_buscador_ms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Bienvenido a la API de relatosdepapel-buscador-ms. Visita /books para ver los libros.";
    }
}