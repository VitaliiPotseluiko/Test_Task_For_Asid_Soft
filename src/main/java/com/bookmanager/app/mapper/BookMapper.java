package com.bookmanager.app.mapper;

import com.bookmanager.app.dto.BookRequestDto;
import com.bookmanager.app.dto.BookResponseDto;
import com.bookmanager.app.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<BookRequestDto, Book, BookResponseDto> {
    @Override
    public Book toModel(BookRequestDto requestDto) {
        Book book = new Book();
        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setPublicationYear(requestDto.getPublicationYear());
        book.setGenre(requestDto.getGenre());
        book.setIsbn(requestDto.getIsbn());
        return book;
    }

    @Override
    public BookResponseDto toDto(Book model) {
        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setId(model.getId());
        responseDto.setAuthor(model.getAuthor());
        responseDto.setTitle(model.getTitle());
        responseDto.setIsbn(model.getIsbn());
        responseDto.setGenre(model.getGenre());
        responseDto.setPublicationYear(model.getPublicationYear());
        return responseDto;
    }
}
