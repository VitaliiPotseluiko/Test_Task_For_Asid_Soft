package com.bookmanager.app.specification.builder.impl;

import com.bookmanager.app.dto.BookSearchParametersDto;
import com.bookmanager.app.model.Book;
import com.bookmanager.app.specification.SpecificationProviderManager;
import com.bookmanager.app.specification.builder.SpecificationBuilder;
import com.bookmanager.app.specification.provider.impl.AuthorSpecificationProvider;
import com.bookmanager.app.specification.provider.impl.GenreSpecificationProvider;
import com.bookmanager.app.specification.provider.impl.TitleSpecificationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto parametersDto) {
        Specification<Book> specification = Specification.where(null);
        if (parametersDto.getAuthors() != null && parametersDto.getAuthors().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(AuthorSpecificationProvider.AUTHOR_KEY)
                    .getSpecification(parametersDto.getAuthors()));
        }

        if (parametersDto.getTitles() != null && parametersDto.getTitles().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(TitleSpecificationProvider.TITLE_KEY)
                    .getSpecification(parametersDto.getTitles()));
        }

        if (parametersDto.getGenres() != null && parametersDto.getGenres().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(GenreSpecificationProvider.GENRE_KEY)
                    .getSpecification(parametersDto.getGenres()));
        }

        return specification;
    }
}
