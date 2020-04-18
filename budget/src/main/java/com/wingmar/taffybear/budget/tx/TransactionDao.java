package com.wingmar.taffybear.budget.tx;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionDao {
    Transaction insert(UnidentifiableTransaction unidentifiableTransaction);

    Optional<Transaction> find(UUID id);

    /**
     * Finds the transactions in the time window between lower and upper (inclusive)
     *
     * @param lower
     * @param upper
     * @return
     */
    List<Transaction> find(LocalDate lower, LocalDate upper);
}
