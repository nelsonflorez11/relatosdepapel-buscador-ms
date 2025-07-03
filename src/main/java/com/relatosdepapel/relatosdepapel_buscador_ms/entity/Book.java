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

//import jakarta.persistence.*;
@Document(indexName = "Book", createIndex = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//@Entity
//@Table(name = "book")
public class Book {
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long ISBN;
    private String image;
    @Field(type = FieldType.Text, name = "author")
    private String author;
    @Field(type = FieldType.Text, name = "title")
    private String title;
    @Field(type = FieldType.Search_As_You_Type, name = "description")
    private String description;
    @Field(type = FieldType.Keyword, name = "type")
    private String type;
    private Double price;
    private String editorial;
    private String idioma;
    private Integer paginas;
    private Integer publicacion;
    private String formato;
    @Field(type = FieldType.Keyword, name = "categoria")
    private String categoria;
    private Integer valoracion;
    private Integer stock;
    @Field(type = FieldType.Boolean, name = "active")
    private Boolean active;
//    public Book() {}
//    Getters y Setters
//    public Long getISBN() {
//        return ISBN;
//    }
//
//    public void setISBN(Long ISBN) {
//        this.ISBN = ISBN;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        this.price = price;
//    }
//
//    public String getEditorial() {
//        return editorial;
//    }
//
//    public void setEditorial(String editorial) {
//        this.editorial = editorial;
//    }
//
//    public String getIdioma() {
//        return idioma;
//    }
//
//    public void setIdioma(String idioma) {
//        this.idioma = idioma;
//    }
//
//    public Integer getPaginas() {
//        return paginas;
//    }
//
//    public void setPaginas(Integer paginas) {
//        this.paginas = paginas;
//    }
//
//    public Integer getPublicacion() {
//        return publicacion;
//    }
//
//    public void setPublicacion(Integer publicacion) {
//        this.publicacion = publicacion;
//    }
//
//    public String getFormato() {
//        return formato;
//    }
//
//    public void setFormato(String formato) {
//        this.formato = formato;
//    }
//
//    public String getCategoria() {
//        return categoria;
//    }
//
//    public void setCategoria(String categoria) {
//        this.categoria = categoria;
//    }
//
//    public Integer getValoracion() {
//        return valoracion;
//    }
//
//    public void setValoracion(Integer valoracion) {
//        this.valoracion = valoracion;
//    }
//
//    public Integer getStock() {
//        return stock;
//    }
//
//    public void setStock(Integer stock) {
//        this.stock = stock;
//    }
//
//    public Boolean getActive() {
//        return active;
//    }
//
//    public void setActive(Boolean active) {
//        this.active = active;
//    }
}