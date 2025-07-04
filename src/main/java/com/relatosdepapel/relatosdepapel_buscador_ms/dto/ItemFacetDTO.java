package com.relatosdepapel.relatosdepapel_buscador_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemFacetDTO {
    private String nombre;
    private long cantidad;
}

