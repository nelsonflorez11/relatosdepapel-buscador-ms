package com.relatosdepapel.relatosdepapel_buscador_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.relatosdepapel.relatosdepapel_buscador_ms.controller",
        "com.relatosdepapel.relatosdepapel_buscador_ms.repository",
        "com.relatosdepapel.relatosdepapel_buscador_ms.entity"
})
public class RelatosdepapelBuscadorMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelatosdepapelBuscadorMsApplication.class, args);
	}

}
