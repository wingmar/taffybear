package com.wingmar.taffybear.budget;

import java.util.UUID;

public interface TransactionDao {
    IdentifiableTransaction insert(Transaction transaction);

    IdentifiableTransaction find(UUID id);
}
