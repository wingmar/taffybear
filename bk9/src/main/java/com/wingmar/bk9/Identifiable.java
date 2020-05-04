package com.wingmar.bk9;

import java.util.UUID;

public interface Identifiable<T> {
    UUID getId();

    T identify(UUID id);
}
