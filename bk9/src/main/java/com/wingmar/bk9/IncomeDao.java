package com.wingmar.bk9;

import java.util.Optional;
import java.util.UUID;

public interface IncomeDao {
    UUID insert(Income income);

    Optional<Income> find(UUID id);
}
