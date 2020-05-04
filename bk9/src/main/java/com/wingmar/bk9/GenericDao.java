package com.wingmar.bk9;

import java.util.Optional;
import java.util.UUID;

public interface GenericDao<T> {
    UUID insert(T t);

    Optional<T> find(UUID id);
}
