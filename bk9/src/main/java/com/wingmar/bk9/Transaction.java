package com.wingmar.bk9;

import com.google.common.base.MoreObjects;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

class Transaction {
    private final UUID id;
    private final LocalDate date;
    private final BigDecimal amount;
    private final String note;

    Transaction(UUID id, LocalDate date, BigDecimal amount, String note) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.note = note;
    }

    UUID getId() {
        return id;
    }

    LocalDate getDate() {
        return date;
    }

    BigDecimal getAmount() {
        return amount;
    }

    String getNote() {
        return note;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Transaction o = (Transaction) obj;
        return EqualsHelper.elementPairsAreEqual(id, o.id, date, o.date, amount, o.amount, note, o.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, amount, note);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(id)
                .addValue(date)
                .addValue(amount)
                .addValue(note)
                .toString();
    }
}
