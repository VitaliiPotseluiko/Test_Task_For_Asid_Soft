package com.bookmanager.app.dto;

import lombok.Data;

@Data
public class BookSearchParametersDto {
    private String[] authors;
    private String[] titles;
    private String[] genres;
}
