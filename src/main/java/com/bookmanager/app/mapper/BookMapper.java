package com.bookmanager.app.mapper;

import com.bookmanager.app.config.MapperConfig;
import com.bookmanager.app.dto.BookRequestDto;
import com.bookmanager.app.dto.BookResponseDto;
import com.bookmanager.app.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    Book toModel(BookRequestDto requestDto);

    BookResponseDto toDto(Book model);
}
