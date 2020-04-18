package com.wingmar.taffybear.budget.tx;

import java.time.LocalDate;

public class DaoBasedTransactionService implements TransactionService {

    private final TransactionDao transactionDao;

    DaoBasedTransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public Transactions find(LocalDate lower, LocalDate upper) {
        return Transactions.ofIterable(transactionDao.find(lower, upper));
    }
}
