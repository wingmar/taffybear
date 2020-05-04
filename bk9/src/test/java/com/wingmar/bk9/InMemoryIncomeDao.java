package com.wingmar.bk9;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryIncomeDao implements IncomeDao {

    private final Map<UUID, Income> map;

    private InMemoryIncomeDao(Map<UUID, Income> map) {
        this.map = map;
    }

    private InMemoryIncomeDao() {
        this(new HashMap<>());
    }

    public static InMemoryIncomeDao fresh() {
        return new InMemoryIncomeDao();
    }

    @Override
    public UUID insert(Income income) {
        Preconditions.checkArgument(income.getId() == null);
        final UUID id = UUID.randomUUID();
        map.put(id, Income.create(id, income.getDate(), income.getAmount(), income.getNote(), income.isCash()));
        return id;
    }

    @Override
    public Optional<Income> find(UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    @VisibleForTesting
    Map<UUID, Income> getMap() {
        return map;
    }
}
