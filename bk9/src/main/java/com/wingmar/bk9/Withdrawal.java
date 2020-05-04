package com.wingmar.bk9;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

class Withdrawal extends Transaction implements Identifiable<Withdrawal> {
    private Withdrawal(UUID id, LocalDate date, BigDecimal amount, String note) {
        super(id, date, amount, note);
    }

    static Withdrawal unidentified(LocalDate date, BigDecimal amount, String note) {
        return create(null, date, amount, note);
    }

    static Withdrawal create(UUID id, LocalDate date, BigDecimal amount, String note) {
        return new Withdrawal(id, date, amount, note);
    }

    @Override
    public Withdrawal identify(UUID id) {
        final Transaction identify = super.identify(id);
        return new Withdrawal(identify.getId(), identify.getDate(), identify.getAmount(), identify.getNote());
    }
}
