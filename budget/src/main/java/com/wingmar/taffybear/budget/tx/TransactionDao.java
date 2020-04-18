package com.wingmar.taffybear.budget.tx;

import java.util.UUID;

public interface TransactionDao {
    Transaction insert(UnidentifiableTransaction unidentifiableTransaction);

    Transaction find(UUID id);
}
