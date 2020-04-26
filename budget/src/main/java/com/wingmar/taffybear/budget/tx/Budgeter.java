package com.wingmar.taffybear.budget.tx;

import java.io.File;
import java.io.IOException;

public class Budgeter {

    private final TransactionService transactionService;

    private Budgeter(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public static Budgeter create(TransactionService transactionService) {
        return new Budgeter(transactionService);
    }

    public void run(File file, boolean includeHeaders) throws IOException {
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions
                .fromFile(file, includeHeaders);
        System.out.println(String.format("Found %d transactions across the following date range: %s. Uploading...",
                unidentifiableTransactions.size(), unidentifiableTransactions.getDateRange()));
        final Transactions transactions = transactionService.uploadCsv(file, includeHeaders);

        if (transactions.isEmpty()) {
            System.out.println("No transactions uploaded.");
        } else {
            System.out.println(String.format("Uploaded %d transaction(s) across the following date range: %s.",
                    transactions.size(), transactions.getDateRange()));
            transactions.forEach(System.out::println);
        }
    }
}