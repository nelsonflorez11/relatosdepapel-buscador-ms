package com.relatosdepapel.relatosdepapel_buscador_ms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(indexName = "books", createIndex = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String ISBN;
    @Field(type = FieldType.Keyword, name = "image")
    private String image;
    @Field(type = FieldType.Text, name = "author")
    private String author;
    @Field(type = FieldType.Text, name = "title")
    private String title;
    @Field(type = FieldType.Search_As_You_Type, name = "description")
    private String description;
    @Field(type = FieldType.Keyword, name = "type")
    private String type;
    @Field(type = FieldType.Float, name = "price")
    private Double price;
    @Field(type = FieldType.Text, name = "editorial")
    private String editorial;
    @Field(type = FieldType.Keyword, name = "idioma")
    private String idioma;
    @Field(type = FieldType.Integer, name = "paginas")
    private Integer paginas;
    @Field(type = FieldType.Integer, name = "publicacion")
    private Integer publicacion;
    @Field(type = FieldType.Keyword, name = "formato")
    private String formato;
    @Field(type = FieldType.Keyword, name = "categoria")
    private String categoria;
    @Field(type = FieldType.Integer, name = "valoracion")
    private Integer valoracion;
    @Field(type = FieldType.Integer, name = "stock")
    private Integer stock;
    @Field(type = FieldType.Boolean, name = "active")
    private Boolean active;

}