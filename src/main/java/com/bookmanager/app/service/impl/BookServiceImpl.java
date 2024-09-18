package com.bookmanager.app.service.impl;

import com.bookmanager.app.dto.BookRequestDto;
import com.bookmanager.app.dto.BookResponseDto;
import com.bookmanager.app.dto.BookSearchParametersDto;
import com.bookmanager.app.exception.UniqueIsbnException;
import com.bookmanager.app.mapper.BookMapper;
import com.bookmanager.app.model.Book;
import com.bookmanager.app.repository.BookRepository;
import com.bookmanager.app.service.BookService;
import com.bookmanager.app.specification.builder.impl.BookSpecificationBuilder;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookResponseDto save(BookRequestDto requestDto) {
        if (bookRepository.findByIsbn(requestDto.getIsbn()).isPresent()) {
            throw new UniqueIsbnException("Can't create. Book with isbn "
                    + requestDto.getIsbn() + " is exist!");
        }
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(requestDto)));
    }

    @Override
    public List<BookResponseDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't delete book by id " + id + ". Wrong id");
        }
        bookRepository.deleteById(id);
    }

    @Override
    public BookResponseDto getById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id " + id + " .Wrong id")
        ));
    }

    @Override
    public BookResponseDto update(Long id, BookRequestDto requestDto) {
        if (bookRepository.existsById(id)) {
            Book book = bookMapper.toModel(requestDto);
            book.setId(id);
            return bookMapper.toDto(bookRepository.save(book));
        }
        throw new EntityNotFoundException("Can't update book by id " + id + ". Wrong id");
    }

    @Override
    public List<BookResponseDto> search(BookSearchParametersDto parametersDto) {
        Specification<Book> specification = bookSpecificationBuilder.build(parametersDto);
        return bookRepository.findAll(specification).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
