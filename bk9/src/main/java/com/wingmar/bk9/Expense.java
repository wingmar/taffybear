package com.wingmar.bk9;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

class Expense extends Transaction {
    private Expense(UUID id, LocalDate date, BigDecimal amount, String note) {
        super(id, date, amount, note);
    }

    static Expense create(UUID id, LocalDate date, BigDecimal amount, String note) {
        return new Expense(id, date, amount, note);
    }

    static Expense unidentified(LocalDate date, BigDecimal amount, String note) {
        return create(null, date, amount, note);
    }
}
