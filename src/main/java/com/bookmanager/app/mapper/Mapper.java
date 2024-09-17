package com.bookmanager.app.mapper;

public interface Mapper<R, M, D> {
    M toModel(R requestDto);

    D toDto(M model);
}
