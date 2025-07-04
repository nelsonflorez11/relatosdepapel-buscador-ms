package com.relatosdepapel.relatosdepapel_buscador_ms.repository;

import java.util.*;

import com.relatosdepapel.relatosdepapel_buscador_ms.entity.Book;
import com.relatosdepapel.relatosdepapel_buscador_ms.controller.model.AggregationDetails;
import com.relatosdepapel.relatosdepapel_buscador_ms.controller.model.BookQueryResponse;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DataAccessRepository {

    @Value("${server.fullAddress}")
    private String serverFullAddress;

    private final Book_ElasticSearch_Repository bookRepository;
    private final ElasticsearchOperations elasticClient;

    private final String[] descriptionSearchFields = {"description", "description._2gram", "description._3gram"};

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Boolean delete(Book book) {
        bookRepository.delete(book);
        return Boolean.TRUE;
    }

	public Optional<Book> findById(String id) {
		return bookRepository.findById(id);
	}

    @SneakyThrows
    public BookQueryResponse findBooks(String author, String description, String titulo, String categoria, String tipo, Float priceMin, Float priceMax, Boolean aggregate) {

        BoolQueryBuilder querySpec = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(author)) {
            querySpec.must(QueryBuilders.matchQuery("author", author));
        }

        if (!StringUtils.isEmpty(titulo)) {
            querySpec.must(QueryBuilders.matchQuery("title", titulo));
        }

        if (!StringUtils.isEmpty(description)) {
            querySpec.must(QueryBuilders.multiMatchQuery(description, descriptionSearchFields).type(Type.BOOL_PREFIX));
        }
        if (!StringUtils.isEmpty(categoria)) {
            querySpec.must(QueryBuilders.termQuery("categoria", categoria));
        }
        if (!StringUtils.isEmpty(tipo)) {
            String[] valoresSeparados = tipo.split(",");
            List<String> tiposList = Arrays.asList(valoresSeparados);
            querySpec.must(QueryBuilders.termsQuery("type", tiposList));
        }

        if (priceMin != null || priceMax != null) {
            RangeQueryBuilder priceRange = QueryBuilders.rangeQuery("price");
            if (priceMin != null) priceRange.gte(priceMin);
            if (priceMax != null) priceRange.lte(priceMax);
            querySpec.must(priceRange);
        }


        //Si no se recibe ningun parametro, busco todos los elementos.
        if (!querySpec.hasClauses()) {
            querySpec.must(QueryBuilders.matchAllQuery());
        }

        //Filtro implicito
        //No le pido al usuario que lo introduzca pero lo aplicamos proactivamente en todas las peticiones
        //En este caso, que los libros sean visibles (estado correcto de la entidad)
        querySpec.must(QueryBuilders.termQuery("active", true));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(querySpec);

        if (aggregate) {
            nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("categoria Aggregation").field("categoria").size(1000));
            // Mas info sobre size 1000 - https://www.elastic.co/guide/en/elasticsearch/reference/7.10/search-aggregations-bucket-terms-aggregation.html#search-aggregations-bucket-terms-aggregation-size
            nativeSearchQueryBuilder.withMaxResults(0);
        }

        //Opcionalmente, podemos paginar los resultados
        //nativeSearchQueryBuilder.withPageable(PageRequest.of(0, 10));
        //Pagina 0, 10 elementos por pagina. El tam de pagina puede venir como qParam y tambien el numero de pagina

        Query query = nativeSearchQueryBuilder.build();
        SearchHits<Book> result = elasticClient.search(query, Book.class);

        List<AggregationDetails> responseAggs = new LinkedList<>();

        if (result.hasAggregations()) {
            Map<String, Aggregation> aggs = result.getAggregations().asMap();
            ParsedStringTerms categoriaAgg = (ParsedStringTerms) aggs.get("categoria Aggregation");

            //Componemos una URI basada en serverFullAddress y query params para cada argumento, siempre que no viniesen vacios
            String queryParams = getQueryParams(author, description, titulo,categoria);
            categoriaAgg.getBuckets()
                    .forEach(
                            bucket -> responseAggs.add(
                                    new AggregationDetails(
                                            bucket.getKey().toString(),
                                            (int) bucket.getDocCount(),
                                            serverFullAddress + "/books?categoria=" + bucket.getKey() + queryParams)));
        }
        return new BookQueryResponse(result.getSearchHits().stream().map(SearchHit::getContent).toList(), responseAggs);
    }

    /**
     * Componemos una URI basada en serverFullAddress y query params para cada argumento, siempre que no viniesen vacios
     *
     * @param author        - autor del libro
     * @param description - descripcion del libro
     * @param titulo     - titulo del libro
     * @param categoria    - categoria del libro
     * @return
     */
    private String getQueryParams(String author, String description, String titulo, String  categoria) {
        String queryParams = (StringUtils.isEmpty(author) ? "" : "&author=" + author)
                + (StringUtils.isEmpty(description) ? "" : "&description=" + description)
                + (StringUtils.isEmpty(titulo) ? "" : "&title=" + titulo);
        // Eliminamos el ultimo & si existe
        return queryParams.endsWith("&") ? queryParams.substring(0, queryParams.length() - 1) : queryParams;
    }
}
