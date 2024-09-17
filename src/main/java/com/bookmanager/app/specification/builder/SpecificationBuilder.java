package com.bookmanager.app.specification.builder;

import com.bookmanager.app.dto.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParametersDto parametersDto);
}