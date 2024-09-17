package com.bookmanager.app.specification.book;

import com.bookmanager.app.exception.SpecificationNotFoundException;
import com.bookmanager.app.model.Book;
import com.bookmanager.app.specification.SpecificationProviderManager;
import com.bookmanager.app.specification.provider.SpecificationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> specificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return specificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(
                        ()-> new SpecificationNotFoundException(
                                "Can't find correct specification provider for key " + key)
                );
    }
}
