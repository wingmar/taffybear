package com.wingmar.taffybear.budget.tx;

import com.google.common.collect.Range;
import java.time.LocalDate;

public interface TransactionService {
    Transactions find(Range<LocalDate> window);
}
