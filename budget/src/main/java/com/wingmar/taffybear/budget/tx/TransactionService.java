package com.wingmar.taffybear.budget.tx;

import java.time.LocalDate;

public interface TransactionService {
    Transactions all();

    Transactions find(LocalDate lower, LocalDate upper);

    void save(UnidentifiableTransactions transactions);
}
