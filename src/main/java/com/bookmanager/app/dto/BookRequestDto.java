package com.bookmanager.app.dto;

import com.bookmanager.app.validation.PublicationYear;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class BookRequestDto {
    @NotEmpty(message = "can't be empty")
    private String title;
    @NotEmpty(message = "can't be empty")
    private String author;
    @NotNull
    @PublicationYear
    private int publicationYear;
    private String genre;
    @NotEmpty(message = "can't be empty")
    @ISBN
    private String isbn;
}
