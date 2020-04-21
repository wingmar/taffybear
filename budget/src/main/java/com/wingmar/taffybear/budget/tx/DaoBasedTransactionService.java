package com.wingmar.taffybear.budget.tx;

import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;

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
    public Transactions save(UnidentifiableTransactions unidentifiableTransactions) {
        final Range<LocalDate> dateRange = unidentifiableTransactions.getDateRange();
        final Transactions existingTransactions = find(dateRange.lowerEndpoint(), dateRange.upperEndpoint());
        final UnidentifiableTransactions existingUnidentifiableTransactions = existingTransactions
                .toUnidentifiableTransactions();
        final Sets.SetView<UnidentifiableTransaction> newTransactions = Sets
                .difference(unidentifiableTransactions.asSet(), existingUnidentifiableTransactions.asSet());

        return Transactions.ofIterable(newTransactions.stream().map(transactionDao::insert)
                .collect(Collectors.toList()));
    }

    @Override
    public Transactions uploadCsv(File file, boolean includeHeaders) throws IOException {
        final Transactions transactions = save(UnidentifiableTransactions.fromFile(file, includeHeaders));
        transactions.forEach(transaction -> transactionDao.logUpload(transaction.getId(), file.getName()));
        return transactions;
    }
}
