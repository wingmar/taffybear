package com.wingmar.bk9;

import org.junit.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class TransactionTest {

    @Test
    public void equals() {
        final UUID id = UUID.randomUUID();
        final Transaction transaction = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");
        final Transaction other = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");

        assertThat(transaction, is(other));
    }

    @Test
    public void equals_null_notEqual() {
        final UUID id = UUID.randomUUID();
        final Transaction transaction = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");

        assertFalse(transaction.equals(null));
    }

    @Test
    public void equals_diffType_notEqual() {
        final UUID id = UUID.randomUUID();
        final Transaction transaction = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");
        final Transaction other = new AnotherTransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffId_notEqual() {
        final Transaction transaction = new TransactionImpl(UUID.randomUUID(), LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");
        final Transaction other = new TransactionImpl(UUID.randomUUID(), LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffDate_notEqual() {
        final UUID id = UUID.randomUUID();
        final Transaction transaction = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");
        final Transaction other = new TransactionImpl(id, LocalDate.parse("2019-10-27"),
                BigDecimal.valueOf(5.4), "note");

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffAmount_notEqual() {
        final UUID id = UUID.randomUUID();
        final Transaction transaction = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");
        final Transaction other = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.5), "note");

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffNote_notEqual() {
        final UUID id = UUID.randomUUID();
        final Transaction transaction = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");
        final Transaction other = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "other note");

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void hash() {
        final UUID id = UUID.randomUUID();
        final Transaction transaction = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");
        final Transaction other = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");

        assertThat(transaction.hashCode(), is(other.hashCode()));
    }

    @Test
    public void hash_diffId_notEqual() {
        final Transaction transaction = new TransactionImpl(UUID.randomUUID(), LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");
        final Transaction other = new TransactionImpl(UUID.randomUUID(), LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffDate_notEqual() {
        final UUID id = UUID.randomUUID();
        final Transaction transaction = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");
        final Transaction other = new TransactionImpl(id, LocalDate.parse("2019-10-27"),
                BigDecimal.valueOf(5.4), "note");

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffAmount_notEqual() {
        final UUID id = UUID.randomUUID();
        final Transaction transaction = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");
        final Transaction other = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.5), "note");

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffNote_notEqual() {
        final UUID id = UUID.randomUUID();
        final Transaction transaction = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");
        final Transaction other = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "other note");

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void testToString() {
        final UUID id = UUID.randomUUID();
        final Transaction transaction = new TransactionImpl(id, LocalDate.parse("2019-10-26"),
                BigDecimal.valueOf(5.4), "note");

        final String actual = transaction.toString();

        assertThat(actual, is(String.format("%s{%s, %s, %s, %s}", TransactionImpl.class.getSimpleName(),
                id, "2019-10-26", 5.4, "note")));
    }

    private class TransactionImpl extends Transaction {
        TransactionImpl(UUID id, LocalDate date, BigDecimal amount, String note) {
            super(id, date, amount, note);
        }
    }

    private class AnotherTransactionImpl extends Transaction {
        AnotherTransactionImpl(UUID id, LocalDate date, BigDecimal amount, String note) {
            super(id, date, amount, note);
        }
    }
}