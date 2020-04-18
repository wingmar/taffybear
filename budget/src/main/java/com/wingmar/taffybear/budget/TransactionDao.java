package com.wingmar.taffybear.budget;

import java.util.UUID;

public interface TransactionDao {
    Transaction insert(UnidentifiableTransaction unidentifiableTransaction);

    Transaction find(UUID id);
}
