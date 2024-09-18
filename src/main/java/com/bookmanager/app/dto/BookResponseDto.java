package com.bookmanager.app.dto;

import lombok.Data;

@Data
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
    private String genre;
    private String isbn;
}
