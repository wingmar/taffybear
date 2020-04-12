package com.wingmar.taffybear.budget;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class TransactionTest {

    @Test
    public void equals() {
        final Transaction transaction = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final Transaction other = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(transaction, is(other));
    }

    @Test
    public void equals_diffAmount() {
        final Transaction transaction = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(-34)),
                "cat", TransactionType.SALE);
        final Transaction other = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffDate() {
        final Transaction transaction = Transaction.createTransaction("test", LocalDate.of(2019, 10, 27), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final Transaction other = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffDesc() {
        final Transaction transaction = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final Transaction other = Transaction.createTransaction("other test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffCategory() {
        final Transaction transaction = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final Transaction other = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat2", TransactionType.SALE);

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void equals_diffType() {
        final Transaction transaction = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final Transaction other = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.RETURN);

        assertThat(transaction, is(not(other)));
    }

    @Test
    public void hash() {
        final Transaction transaction = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final Transaction other = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(transaction.hashCode(), is(other.hashCode()));
    }

    @Test
    public void hash_diffAmount() {
        final Transaction transaction = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(-34)),
                "cat", TransactionType.SALE);
        final Transaction other = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffDate() {
        final Transaction transaction = Transaction.createTransaction("test", LocalDate.of(2019, 10, 27), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final Transaction other = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffDesc() {
        final Transaction transaction = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final Transaction other = Transaction.createTransaction("other test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffCategory() {
        final Transaction transaction = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final Transaction other = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat2", TransactionType.SALE);

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffType() {
        final Transaction transaction = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final Transaction other = Transaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.RETURN);

        assertThat(transaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void createUsdTransaction() {
        final Transaction transaction = Transaction.createUsdTransaction("some description", LocalDate.of(2020, 10, 26), BigDecimal.valueOf(54.3),
                "cat", TransactionType.SALE);

        assertThat(transaction, is(Transaction.createTransaction("some description", LocalDate.of(2020, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(54.3)),
                "cat", TransactionType.SALE)));
    }
}