package com.wingmar.taffybear.budget.tx;

import com.google.common.collect.ImmutableList;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class UnidentifiableTransactionsTest {

    @Test
    public void equals() {
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.of(Collections
                .singletonList(UnidentifiableTransaction.createTransaction(Merchant.named("merchant"), LocalDate.of
                                (2020, 12, 1),
                        Money.of(CurrencyUnit.USD,
                                BigDecimal.valueOf(2.5).setScale(2, RoundingMode.HALF_UP)), Category.named("category"),
                        TransactionType.SALE)));
        final UnidentifiableTransactions other = UnidentifiableTransactions.of(Collections.singletonList
                (UnidentifiableTransaction.createTransaction(Merchant.named("merchant"), LocalDate.of(2020, 12, 1),
                        Money.of
                        (CurrencyUnit.USD,
                                BigDecimal.valueOf(2.5).setScale(2, RoundingMode.HALF_UP)), Category.named("category"), TransactionType.SALE)));

        assertThat(unidentifiableTransactions, is(other));
    }

    @Test
    public void equals_null() {
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.of(Collections
                .singletonList(TestDataGenerator.newInstance().randomUnidentifiableTransaction()));

        assertFalse(unidentifiableTransactions.equals(null));
    }

    @Test
    public void hash() {
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.of(Collections
                .singletonList(UnidentifiableTransaction.createTransaction(Merchant.named("merchant"), LocalDate.of
                                (2020, 12, 1), Money.of(CurrencyUnit.USD,
                        BigDecimal.valueOf(2.5).setScale(2, RoundingMode.HALF_UP)), Category.named("category"),
                        TransactionType.SALE)));
        final UnidentifiableTransactions other = UnidentifiableTransactions.of(Collections.singletonList
                (UnidentifiableTransaction.createTransaction(Merchant.named("merchant"), LocalDate.of(2020, 12, 1),
                        Money.of(CurrencyUnit.USD,
                                BigDecimal.valueOf(2.5).setScale(2, RoundingMode.HALF_UP)), Category.named("category"), TransactionType.SALE)));

        assertThat(unidentifiableTransactions.hashCode(), is(other.hashCode()));
    }

    @Test
    public void parseFromFile() throws URISyntaxException, IOException {
        // given
        final File file = new File(getClass().getResource("transactions.csv").toURI());

        // when
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.fromFile(file, true);

        // then
        final UnidentifiableTransaction t0 = UnidentifiableTransaction.createTransaction(Merchant.named
                        ("FIRSTENERGY/EZPAYRECUR"), LocalDate.of(2020, 3, 30), Money.of(CurrencyUnit.USD,
                BigDecimal.valueOf(-66.28).setScale(2, RoundingMode.HALF_UP)), Category.named("Bills & Utilities"),
                TransactionType.SALE);
        final UnidentifiableTransaction t1 = UnidentifiableTransaction.createTransaction(Merchant.named("CHEWY.COM"),
                LocalDate.of(2020, 3, 30), Money.of(CurrencyUnit.USD,
                        BigDecimal.valueOf(35.28).setScale(2, RoundingMode.HALF_UP)), Category.named("Shopping"),
                TransactionType.RETURN);
        final UnidentifiableTransaction t2 = UnidentifiableTransaction.createTransaction(Merchant.named("VONEIFF " +
                "OIL"), LocalDate.of(2020, 3, 23), Money.of(CurrencyUnit.USD,
                BigDecimal.valueOf(-344.85).setScale(2, RoundingMode.HALF_UP)), Category.named("Home"), TransactionType.SALE);
        assertThat(unidentifiableTransactions, is(UnidentifiableTransactions.of(ImmutableList.of(t0, t1, t2))));
    }

    @Test
    public void fromInputStream() throws IOException, URISyntaxException {
        // given

        // when
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.fromInputStream(new
                FileInputStream(new File(getClass()
                .getResource("transactions.csv").toURI())), true);

        // then
        final UnidentifiableTransaction t0 = UnidentifiableTransaction.createTransaction(Merchant.named
                        ("FIRSTENERGY/EZPAYRECUR"), LocalDate.of(2020, 3, 30), Money.of(CurrencyUnit.USD,
                BigDecimal.valueOf(-66.28).setScale(2, RoundingMode.HALF_UP)), Category.named("Bills & Utilities"),
                TransactionType.SALE);
        final UnidentifiableTransaction t1 = UnidentifiableTransaction.createTransaction(Merchant.named("CHEWY.COM"),
                LocalDate.of(2020, 3, 30), Money.of(CurrencyUnit.USD,
                        BigDecimal.valueOf(35.28).setScale(2, RoundingMode.HALF_UP)), Category.named("Shopping"),
                TransactionType.RETURN);
        final UnidentifiableTransaction t2 = UnidentifiableTransaction.createTransaction(Merchant.named("VONEIFF " +
                "OIL"), LocalDate.of(2020, 3, 23), Money.of(CurrencyUnit.USD,
                BigDecimal.valueOf(-344.85).setScale(2, RoundingMode.HALF_UP)), Category.named("Home"), TransactionType.SALE);
        assertThat(unidentifiableTransactions, is(UnidentifiableTransactions.of(ImmutableList.of(t0, t1, t2))));
    }

    @Test
    public void asInputStream() throws IOException {
        // given
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.of(Collections
                .singletonList(UnidentifiableTransaction.createTransaction(Merchant.named("someMerchantName"),
                        LocalDate.of(2020, 12, 1), Money.of(CurrencyUnit.USD,
                                BigDecimal.valueOf(2.5).setScale(2, RoundingMode.HALF_UP)), Category.named("category"), TransactionType.SALE)));

        // when
        final InputStream actual = unidentifiableTransactions.asInputStream();

        // then
        final UnidentifiableTransactions fromInputStream = UnidentifiableTransactions.fromInputStream(actual, false);
        assertThat(fromInputStream, is(unidentifiableTransactions));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void asList_immutable() {
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.of(Collections
                .singletonList(UnidentifiableTransaction.createTransaction(Merchant.named("merchant"), LocalDate.of
                        (2020, 12, 1), Money.of(CurrencyUnit.USD,
                        BigDecimal.valueOf(2.5).setScale(2, RoundingMode.HALF_UP)), Category.named("category"), TransactionType.SALE)));

        unidentifiableTransactions.asList().clear();
    }
}