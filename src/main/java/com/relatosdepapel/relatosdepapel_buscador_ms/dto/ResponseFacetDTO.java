package com.relatosdepapel.relatosdepapel_buscador_ms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFacetDTO {
    private List<ItemFacetDTO> formatos;
    private List<ItemFacetDTO> idiomas;
    private List<ItemFacetDTO> categorias;
    private List<ItemFacetDTO> autores;
}
