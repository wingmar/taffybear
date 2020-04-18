package com.wingmar.taffybear.budget;

import java.util.UUID;

public interface TransactionDao {
    IdentifiableTransaction insert(UnidentifiableTransaction unidentifiableTransaction);

    IdentifiableTransaction find(UUID id);
}
