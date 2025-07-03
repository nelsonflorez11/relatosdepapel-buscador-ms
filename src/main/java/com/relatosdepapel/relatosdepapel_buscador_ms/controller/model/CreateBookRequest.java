package com.relatosdepapel.relatosdepapel_buscador_ms.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

	private String author;
	private String title;
	private String description;
	private Boolean active;
	private String type;
	private Double price;
	private String editorial;
	private String idioma;
	private Integer paginas;
	private Integer publicacion;
	private String formato;
	private String categoria;
	private Integer valoracion;
	private Integer stock;
}
