package com.wingmar.bk9;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class IncomeTest {

    @Test
    public void unidentified() {
        final Income transaction = Income.unidentified(LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(null, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);

        assertThat(transaction, is(other));
    }

    @Test
    public void equals() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);

        assertThat(transaction, is(other));
    }

    @Test
    public void equals_null_notEqual() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);

        assertFalse(transaction.equals(null));
    }

    @Test
    public void equals_diffType_notEqual() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final TransactionImpl other = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffId_notEqual() {
        final Income transaction = Income.create(UUID.randomUUID(), LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(UUID.randomUUID(), LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffDate_notEqual() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(id, LocalDate.parse("2019-10-27"),
                BigDecimal.valueOf(5.4), "note", true);

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffAmount_notEqual() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.5), "note", true);

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffNote_notEqual() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "other note", true);

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffCash_notEqual() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", false);

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void hash() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);

        assertThat(transaction.hashCode(), is(other.hashCode()));
    }

    @Test
    public void hash_diffId_notEqual() {
        final Income transaction = Income.create(UUID.randomUUID(), LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(UUID.randomUUID(), LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffDate_notEqual() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(id, LocalDate.parse("2019-10-27"),
                BigDecimal.valueOf(5.4), "note", true);

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffAmount_notEqual() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.5), "note", true);

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffNote_notEqual() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "other note", true);

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffCash_notEqual() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);
        final Income other = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", false);

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void testToString() {
        final UUID id = UUID.randomUUID();
        final Income transaction = Income.create(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note", true);

        final String actual = transaction.toString();

        assertThat(actual, is(String.format("%s{%s, %s, %s, %s, %s}", Income.class.getSimpleName(),
                id, "2019-10-26", 5.4, "note", true)));
    }

    private static class TransactionImpl extends Transaction {
        TransactionImpl(UUID id, LocalDate date, BigDecimal amount, String note) {
            super(id, date, amount, note);
        }
    }
}