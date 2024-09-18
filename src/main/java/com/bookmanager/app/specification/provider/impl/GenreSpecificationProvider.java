package com.bookmanager.app.specification.provider.impl;

import com.bookmanager.app.model.Book;
import com.bookmanager.app.specification.provider.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class GenreSpecificationProvider implements SpecificationProvider<Book> {
    public static final String GENRE_KEY = "genre";

    @Override
    public String getKey() {
        return GENRE_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] genres) {
        return (root, query, criteriaBuilder) -> root
                .get(GENRE_KEY)
                .in(Arrays.stream(genres).toArray());
    }
}
