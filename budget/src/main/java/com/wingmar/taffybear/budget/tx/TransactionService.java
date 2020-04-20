package com.wingmar.taffybear.budget.tx;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

interface TransactionService {
    Transactions all();

    Transactions find(LocalDate lower, LocalDate upper);

    Transactions save(UnidentifiableTransactions transactions);

    Transactions uploadCsv(File file, boolean includeHeaders) throws IOException;
}
