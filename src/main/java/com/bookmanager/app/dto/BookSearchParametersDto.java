package com.bookmanager.app.dto;

import lombok.Data;

@Data
public class BookSearchParametersDto {
    String[] authors;
    String[] titles;
    String[] genres;
}
