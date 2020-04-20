package com.wingmar.taffybear.budget.tx;

import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.time.LocalDate;

public class DaoBasedTransactionService implements TransactionService {

    private final TransactionDao transactionDao;

    DaoBasedTransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public Transactions all() {
        return Transactions.ofIterable(transactionDao.all());
    }

    @Override
    public Transactions find(LocalDate lower, LocalDate upper) {
        return Transactions.ofIterable(transactionDao.find(lower, upper));
    }

    @Override
    public void save(UnidentifiableTransactions unidentifiableTransactions) {
        final Range<LocalDate> dateRange = unidentifiableTransactions.getDateRange();
        final Transactions existingTransactions = find(dateRange.lowerEndpoint(), dateRange.upperEndpoint());
        final UnidentifiableTransactions existingUnidentifiableTransactions = existingTransactions
                .toUnidentifiableTransactions();
        final Sets.SetView<UnidentifiableTransaction> newTransactions = Sets
                .difference(unidentifiableTransactions.asSet(), existingUnidentifiableTransactions.asSet());

        newTransactions.forEach(transactionDao::insert);
    }
}
