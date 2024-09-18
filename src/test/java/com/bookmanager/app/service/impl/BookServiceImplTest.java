package com.bookmanager.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.bookmanager.app.dto.BookRequestDto;
import com.bookmanager.app.dto.BookResponseDto;
import com.bookmanager.app.mapper.BookMapper;
import com.bookmanager.app.model.Book;
import com.bookmanager.app.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    BookServiceImpl bookService;

    @Test
    @DisplayName("""
            update book with id = 1
            """)
    public void update_UpdateBookWithExistingId_Ok() {
        Long id = 1L;
        BookRequestDto bookRequestDto = createBookRequestDto();
        Book currentBook = createBook(id);

        Book updatedBook = new Book();
        updatedBook.setId(currentBook.getId());
        updatedBook.setAuthor(bookRequestDto.getAuthor());
        updatedBook.setTitle(bookRequestDto.getTitle());
        updatedBook.setGenre(bookRequestDto.getGenre());
        updatedBook.setIsbn(bookRequestDto.getIsbn());
        updatedBook.setPublicationYear(bookRequestDto.getPublicationYear());

        BookResponseDto expected = new BookResponseDto();
        expected.setId(currentBook.getId());
        expected.setAuthor(bookRequestDto.getAuthor());
        expected.setTitle(bookRequestDto.getTitle());
        expected.setGenre(bookRequestDto.getGenre());
        expected.setIsbn(bookRequestDto.getIsbn());
        expected.setPublicationYear(bookRequestDto.getPublicationYear());

        when(bookRepository.existsById(id)).thenReturn(true);
        when(bookMapper.toModel(bookRequestDto)).thenReturn(currentBook);
        when(bookRepository.save(currentBook)).thenReturn(updatedBook);
        when(bookMapper.toDto(updatedBook)).thenReturn(expected);

        BookResponseDto actual = bookService.update(id, bookRequestDto);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getPublicationYear(), actual.getPublicationYear());
        assertEquals(expected.getGenre(), actual.getGenre());
    }

    @Test
    @DisplayName("""
            update book with wrong id
            """)
    public void update_UpdateBookWithNotExistingId_ThrowsException() {
        Long wrongId = 100L;
        when(bookRepository.existsById(wrongId)).thenReturn(false);

        Exception actual = assertThrows(
                EntityNotFoundException.class, () -> bookService.update(wrongId, any())
        );

        assertEquals("EntityNotFoundException", actual.getClass().getSimpleName());
        assertEquals("Can't update book by id " + wrongId + ". Wrong id", actual.getMessage());
    }

    private BookRequestDto createBookRequestDto() {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setAuthor("Shevchenko Taras");
        bookRequestDto.setTitle("Kobzar");
        bookRequestDto.setGenre("Poems");
        bookRequestDto.setPublicationYear(2000);
        bookRequestDto.setIsbn("978-2-266-11156-0");
        return bookRequestDto;
    }

    private Book createBook(Long id) {
        Book book = new Book();
        book.setId(id);
        book.setAuthor("Shevchenko");
        book.setTitle("Kobzar");
        book.setGenre("Essays");
        book.setPublicationYear(1990);
        book.setIsbn("978-2-266-11156-0");
        return book;
    }
}