package com.wingmar.taffybear.budget.tx;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionDao {
    Transaction insert(UnidentifiableTransaction unidentifiableTransaction);

    boolean logUpload(UUID transactionId, String filename);

    List<Transaction> all();

    Optional<Transaction> find(UUID id);

    /**
     * Finds the transactions in the time window between lower and upper (inclusive)
     *
     * @param lower lower bound
     * @param upper upper bound
     * @return list of transactions during the time period, inclusive
     */
    List<Transaction> find(LocalDate lower, LocalDate upper);
}
