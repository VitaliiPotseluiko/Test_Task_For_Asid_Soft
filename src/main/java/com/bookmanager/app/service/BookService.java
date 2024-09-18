package com.bookmanager.app.service;

import com.bookmanager.app.dto.BookRequestDto;
import com.bookmanager.app.dto.BookResponseDto;
import com.bookmanager.app.dto.BookSearchParametersDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto save(BookRequestDto requestDto);

    List<BookResponseDto> getAll(Pageable pageable);

    void delete(Long id);

    BookResponseDto getById(Long id);

    BookResponseDto update(Long id, BookRequestDto requestDto);

    List<BookResponseDto> search(BookSearchParametersDto parametersDto);
}
