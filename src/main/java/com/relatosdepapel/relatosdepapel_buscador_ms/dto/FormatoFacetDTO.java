package com.relatosdepapel.relatosdepapel_buscador_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FormatoFacetDTO {
	private String formato;
    private long cantidad;
}
