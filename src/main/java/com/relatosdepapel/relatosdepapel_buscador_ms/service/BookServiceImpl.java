package com.relatosdepapel.relatosdepapel_buscador_ms.service;

import com.relatosdepapel.relatosdepapel_buscador_ms.controller.model.BookQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.relatosdepapel.relatosdepapel_buscador_ms.repository.DataAccessRepository;
import com.relatosdepapel.relatosdepapel_buscador_ms.entity.Book;
import  com.relatosdepapel.relatosdepapel_buscador_ms.controller.model.CreateBookRequest;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookServiceElastic {

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

}
