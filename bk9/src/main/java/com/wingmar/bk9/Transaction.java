package com.wingmar.bk9;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

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

    public UUID getId() {
        return id;
    }

    protected Transaction identify(UUID id) {
        Preconditions.checkState(getId() == null);
        return new Transaction(id, getDate(), getAmount(), getNote());
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
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Transaction other = (Transaction) o;
        return EqualsHelper.newInstance()
                .elementPairsAreEqual(id, other.id, date, other.date, amount, other.amount, note, other.note);
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
