package com.wingmar.bk9;

import com.google.common.base.MoreObjects;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

class Income extends Transaction {

    private final boolean cash;

    private Income(UUID id, LocalDate date, BigDecimal amount, String note, boolean cash) {
        super(id, date, amount, note);
        this.cash = cash;
    }

    static Income create(UUID id, LocalDate date, BigDecimal amount, String note, boolean cash) {
        return new Income(id, date, amount, note, cash);
    }

    static Income unidentified(LocalDate date, BigDecimal amount, String note, boolean cash) {
        return new Income(null, date, amount, note, cash);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Income o = (Income) obj;
        return super.equals(o) && Objects.equals(cash, o.cash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getAmount(), getNote(), cash);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(getId())
                .addValue(getDate())
                .addValue(getAmount())
                .addValue(getNote())
                .addValue(cash)
                .toString();
    }
}
