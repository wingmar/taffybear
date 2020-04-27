package com.wingmar.bk9;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

class Expense extends Transaction {
    Expense(UUID id, LocalDate date, BigDecimal amount, String note) {
        super(id, date, amount, note);
    }
}
