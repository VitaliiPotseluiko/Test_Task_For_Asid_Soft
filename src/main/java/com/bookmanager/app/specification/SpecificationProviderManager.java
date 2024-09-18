package com.bookmanager.app.specification;

import com.bookmanager.app.specification.provider.SpecificationProvider;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getSpecificationProvider(String key);
}
