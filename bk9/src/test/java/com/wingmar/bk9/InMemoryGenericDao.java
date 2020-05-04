package com.wingmar.bk9;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryGenericDao<T extends Identifiable<T>> implements GenericDao<T> {
    private final Map<UUID, T> map;

    private InMemoryGenericDao(Map<UUID, T> map) {
        this.map = map;
    }

    InMemoryGenericDao() {
        this(new HashMap<>());
    }

    @Override
    public UUID insert(T t) {
        Preconditions.checkArgument(t.getId() == null);
        final UUID id = UUID.randomUUID();
        final T identified = t.identify(id);
        map.put(id, identified);
        return id;
    }

    @Override
    public Optional<T> find(UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    @VisibleForTesting
    protected Map<UUID, T> getState() {
        return ImmutableMap.copyOf(map);
    }
}
