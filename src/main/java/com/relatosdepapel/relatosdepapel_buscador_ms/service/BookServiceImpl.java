package com.relatosdepapel.relatosdepapel_buscador_ms.service;

import com.relatosdepapel.relatosdepapel_buscador_ms.controller.model.BookQueryResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.relatosdepapel.relatosdepapel_buscador_ms.repository.DataAccessRepository;
import com.relatosdepapel.relatosdepapel_buscador_ms.entity.Book;
import  com.relatosdepapel.relatosdepapel_buscador_ms.controller.model.CreateBookRequest;
import com.relatosdepapel.relatosdepapel_buscador_ms.dto.CategoriaFacetDTO;
import com.relatosdepapel.relatosdepapel_buscador_ms.dto.FormatoFacetDTO;
import com.relatosdepapel.relatosdepapel_buscador_ms.dto.ItemFacetDTO;
import com.relatosdepapel.relatosdepapel_buscador_ms.dto.ResponseFacetDTO;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookServiceElastic {

	private final RestHighLevelClient restHighLevelClient;
	private final DataAccessRepository repository;

	@Override
	public BookQueryResponse getBooks(String author, String description, String titulo, String categoria, Boolean aggregate) {
		//Ahora por defecto solo devolvera libros visibles
		return repository.findBooks(author, description, titulo, categoria, aggregate);
	}

	@Override
	public Book getBook(Long Id) {
		return repository.findById(Id).orElse(null);
	}

	@Override
	public Boolean removeBook(Long Id) {

		Book product = repository.findById(Id).orElse(null);
		if (product != null) {
			repository.delete(product);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public Book createBook(CreateBookRequest request) {

		if (request != null && StringUtils.hasLength(request.getAuthor().trim())
				&& StringUtils.hasLength(request.getDescription().trim())
				&& StringUtils.hasLength(request.getTitle().trim()) && request.getActive() != null) {

			Book product = Book.builder().author(request.getAuthor()).description(request.getDescription())
					.type(request.getType()).active(request.getActive()).build();

			return repository.save(product);
		} else {
			return null;
		}
	}
	
	
	//Facet de categorías de libros
	@Override
	public List<CategoriaFacetDTO> obtenerCategoriasConConteo() throws IOException {
        SearchRequest searchRequest = new SearchRequest("books");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
            .query(new MatchAllQueryBuilder())
            .size(0)
            .aggregation(
                AggregationBuilders.terms("categorias")
                    .field("categoria")
                    .size(10)
            );

        searchRequest.source(sourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Terms categorias = response.getAggregations().get("categorias");

        return categorias.getBuckets().stream()
            .map(bucket -> new CategoriaFacetDTO(bucket.getKeyAsString(), bucket.getDocCount()))
            .collect(Collectors.toList());
    }
	
	//Facet de formatos de libros
	@Override
	public List<FormatoFacetDTO> obtenerFormatosConConteo() throws IOException {
        SearchRequest searchRequest = new SearchRequest("books");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
            .query(new MatchAllQueryBuilder())
            .size(0)
            .aggregation(
                AggregationBuilders.terms("formatos")
                    .field("formato")
                    .size(20)
            );

        searchRequest.source(sourceBuilder);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Terms autores = response.getAggregations().get("formatos");

        return autores.getBuckets().stream()
            .map(bucket -> new FormatoFacetDTO(bucket.getKeyAsString(), bucket.getDocCount()))
            .collect(Collectors.toList());
    }
	
	//Facets de formatos, idiomas, categorias y autores
	@Override
	public ResponseFacetDTO obtenerFacetsCombinados() throws IOException {
	    SearchRequest searchRequest = new SearchRequest("books");

	    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
	        .size(0)
	        .aggregation(AggregationBuilders.terms("formatos").field("formato").size(10))
	        .aggregation(AggregationBuilders.terms("idiomas").field("idioma").size(10))
	        .aggregation(AggregationBuilders.terms("categorias").field("categoria").size(10))
	        .aggregation(AggregationBuilders.terms("autores").field("author.keyword").size(10));

	    searchRequest.source(sourceBuilder);

	    SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

	    return new ResponseFacetDTO(
	        convertirBuckets(response, "formatos"),
	        convertirBuckets(response, "idiomas"),
	        convertirBuckets(response, "categorias"),
	        convertirBuckets(response, "autores")
	    );
	}

	private List<ItemFacetDTO> convertirBuckets(SearchResponse response, String aggName) {
	    Terms terms = response.getAggregations().get(aggName);
	    if (terms == null) return Collections.emptyList();

	    return terms.getBuckets().stream()
	        .map(bucket -> new ItemFacetDTO(bucket.getKeyAsString(), bucket.getDocCount()))
	        .collect(Collectors.toList());
	}


}
