package com.bookmanager.app.controller;

import com.bookmanager.app.dto.BookRequestDto;
import com.bookmanager.app.dto.BookResponseDto;
import com.bookmanager.app.dto.BookSearchParametersDto;
import com.bookmanager.app.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDto createBook(@RequestBody @Valid BookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PutMapping("/{id}")
    public BookResponseDto updateBook(@PathVariable Long id,
                                      @RequestBody @Valid BookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/{id}")
    public BookResponseDto getBook(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping
    public List<BookResponseDto> getAllBooks(Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @GetMapping("/search")
    public List<BookResponseDto> getAllBooksByParameters(BookSearchParametersDto parametersDto) {
        return bookService.search(parametersDto);
    }

}
