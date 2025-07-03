package com.relatosdepapel.relatosdepapel_buscador_ms.controller.model;

import com.relatosdepapel.relatosdepapel_buscador_ms.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookQueryResponse {

    private List<Book> books;
    private List<AggregationDetails> aggs;

}
