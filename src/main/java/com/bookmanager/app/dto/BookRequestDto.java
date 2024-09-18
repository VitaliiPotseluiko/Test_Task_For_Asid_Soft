package com.bookmanager.app.dto;

import com.bookmanager.app.validation.PublicationYear;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class BookRequestDto {
    @NotBlank(message = "can't be empty")
    private String title;
    @NotBlank(message = "can't be empty")
    private String author;
    @PublicationYear
    private int publicationYear;
    private String genre;
    @NotEmpty(message = "can't be empty")
    @ISBN
    private String isbn;
}
