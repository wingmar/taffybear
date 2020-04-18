package com.wingmar.taffybear.budget.tx;

import com.google.common.collect.Range;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionDao {
    Transaction insert(UnidentifiableTransaction unidentifiableTransaction);

    Optional<Transaction> find(UUID id);

    List<Transaction> find(Range<LocalDate> dateRange);
}
