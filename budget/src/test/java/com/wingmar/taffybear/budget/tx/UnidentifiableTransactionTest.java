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

    private final TestDataGenerator generator = TestDataGenerator.newInstance();

    @Test
    public void equals() {
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();
        final UnidentifiableTransaction other = UnidentifiableTransaction.copy(unidentifiableTransaction);

        assertThat(unidentifiableTransaction, is(other));
    }

    @Test
    public void equals_diffAmount() {
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction(
                unidentifiableTransaction.getMerchant(), unidentifiableTransaction.getDate(),
                unidentifiableTransaction.getAmount().plus(1), unidentifiableTransaction.getCategory(),
                unidentifiableTransaction.getType()
        );

        assertThat(unidentifiableTransaction, is(not(other)));
    }

    @Test
    public void equals_diffDate() {
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction(
                unidentifiableTransaction.getMerchant(), unidentifiableTransaction.getDate().plusDays(1),
                unidentifiableTransaction.getAmount(), unidentifiableTransaction.getCategory(),
                unidentifiableTransaction.getType()
        );

        assertThat(unidentifiableTransaction, is(not(other)));
    }

    @Test
    public void equals_diffMerchant() {
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction(
                Merchant.named(unidentifiableTransaction.getMerchant().getName() + "other"),
                unidentifiableTransaction.getDate(), unidentifiableTransaction.getAmount(),
                unidentifiableTransaction.getCategory(), unidentifiableTransaction.getType()
        );

        assertThat(unidentifiableTransaction, is(not(other)));
    }

    @Test
    public void equals_diffCategory() {
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction(
                unidentifiableTransaction.getMerchant(), unidentifiableTransaction.getDate(),
                unidentifiableTransaction.getAmount(),
                Category.named(unidentifiableTransaction.getCategory().getName() + "other"),
                unidentifiableTransaction.getType()
        );

        assertThat(unidentifiableTransaction, is(not(other)));
    }

    @Test
    public void equals_diffType() {
        final Merchant merchant = generator.randomMerchant();
        final LocalDate date = generator.randomTwentyFirstCenturyLocalDate();
        final Money amount = generator.randomMoney();
        final Category category = generator.randomCategory();
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction
                .createTransaction(merchant, date, amount, category, TransactionType.PAYMENT);
        final UnidentifiableTransaction other = UnidentifiableTransaction
                .createTransaction(merchant, date, amount, category, TransactionType.SALE);

        assertThat(unidentifiableTransaction, is(not(other)));
    }

    @Test
    public void hash() {
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();
        final UnidentifiableTransaction other = UnidentifiableTransaction.copy(unidentifiableTransaction);

        assertThat(unidentifiableTransaction.hashCode(), is(other.hashCode()));
    }

    @Test
    public void hash_diffAmount() {
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction(
                unidentifiableTransaction.getMerchant(), unidentifiableTransaction.getDate(),
                unidentifiableTransaction.getAmount().plus(1), unidentifiableTransaction.getCategory(),
                unidentifiableTransaction.getType()
        );

        assertThat(unidentifiableTransaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffDate() {
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction(
                unidentifiableTransaction.getMerchant(), unidentifiableTransaction.getDate().plusDays(1),
                unidentifiableTransaction.getAmount(), unidentifiableTransaction.getCategory(),
                unidentifiableTransaction.getType()
        );


        assertThat(unidentifiableTransaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffMerchant() {
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction(
                Merchant.named(unidentifiableTransaction.getMerchant().getName() + "other"),
                unidentifiableTransaction.getDate(), unidentifiableTransaction.getAmount(),
                unidentifiableTransaction.getCategory(), unidentifiableTransaction.getType()
        );


        assertThat(unidentifiableTransaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffCategory() {
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();
        final UnidentifiableTransaction other = UnidentifiableTransaction.createTransaction(
                unidentifiableTransaction.getMerchant(), unidentifiableTransaction.getDate(),
                unidentifiableTransaction.getAmount(),
                Category.named(unidentifiableTransaction.getCategory().getName() + "other"),
                unidentifiableTransaction.getType()
        );

        assertThat(unidentifiableTransaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void hash_diffType() {
        final Merchant merchant = generator.randomMerchant();
        final LocalDate date = generator.randomTwentyFirstCenturyLocalDate();
        final Money amount = generator.randomMoney();
        final Category category = generator.randomCategory();
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction
                .createTransaction(merchant, date, amount, category, TransactionType.PAYMENT);
        final UnidentifiableTransaction other = UnidentifiableTransaction
                .createTransaction(merchant, date, amount, category, TransactionType.SALE);

        assertThat(unidentifiableTransaction.hashCode(), is(not(other.hashCode())));
    }

    @Test
    public void createUsdTransaction() {
        final UnidentifiableTransaction unidentifiableTransaction = UnidentifiableTransaction
                .createUsdTransaction(Merchant.named("some description"),
                        LocalDate.of(2020, 10, 26), BigDecimal.valueOf(54.3),
                        Category.named("cat"), TransactionType.SALE);

        assertThat(unidentifiableTransaction, is(UnidentifiableTransaction.createTransaction(Merchant
                        .named("some description"), LocalDate.of(2020, 10, 26),
                Money.of(CurrencyUnit.USD, BigDecimal.valueOf(54.3)), Category.named("cat"), TransactionType.SALE)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTransaction_nonUsd_illegalArgument() {
        UnidentifiableTransaction.createTransaction(generator.randomMerchant(),
                generator.randomTwentyFirstCenturyLocalDate(), Money.of(CurrencyUnit.EUR, 5),
                generator.randomCategory(), generator.randomTransactionType());
    }
}