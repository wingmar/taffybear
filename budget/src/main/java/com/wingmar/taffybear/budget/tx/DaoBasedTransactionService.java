package com.wingmar.taffybear.budget.tx;

import com.google.common.collect.Range;
import java.time.LocalDate;

public class DaoBasedTransactionService implements TransactionService {

    private final TransactionDao transactionDao;

    DaoBasedTransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public Transactions find(Range<LocalDate> window) {
        return Transactions.ofIterable(transactionDao.find(window));
    }
}
