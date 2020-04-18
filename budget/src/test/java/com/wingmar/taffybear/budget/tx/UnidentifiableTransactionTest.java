package com.wingmar.taffybear.budget.tx;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class UnidentifiableTransactionTest {

    @Test
    public void equals() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(unidentifiableTransaction, is(other));
    }

    @Test
    public void equals_diffAmount() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(-34)),
                "cat", TransactionType.SALE);
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(unidentifiableTransaction, is(not(other)));
    }

    @Test
    public void equals_diffDate() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 27), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(unidentifiableTransaction, is(not(other)));
    }

    @Test
    public void equals_diffDesc() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction("other test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(unidentifiableTransaction, is(not(other)));
    }

    @Test
    public void equals_diffCategory() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat2", TransactionType.SALE);

        assertThat(unidentifiableTransaction, is(not(other)));
    }

    @Test
    public void equals_diffType() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.RETURN);

        assertThat(unidentifiableTransaction, is(not(other)));
    }

    @Test
    public void hash() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(unidentifiableTransaction.hashCode(), is(other.hashCode()));
    }

    @Test
    public void hash_diffAmount() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(-34)),
                "cat", TransactionType.SALE);
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(unidentifiableTransaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffDate() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 27), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(unidentifiableTransaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffDesc() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction("other test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);

        assertThat(unidentifiableTransaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffCategory() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat2", TransactionType.SALE);

        assertThat(unidentifiableTransaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffType() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.SALE);
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction("test", LocalDate.of(2019, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(34)),
                "cat", TransactionType.RETURN);

        assertThat(unidentifiableTransaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void createUsdTransaction() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction.createUsdTransaction("some description", LocalDate.of(2020, 10, 26), BigDecimal.valueOf(54.3),
                "cat", TransactionType.SALE);

        assertThat(unidentifiableTransaction, is(UnidentifiableTransaction.createTransaction("some description", LocalDate.of(2020, 10, 26), Money.of(CurrencyUnit.USD, BigDecimal.valueOf(54.3)),
                "cat", TransactionType.SALE)));
    }
}