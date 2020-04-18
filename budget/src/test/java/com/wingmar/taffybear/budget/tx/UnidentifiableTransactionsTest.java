package com.wingmar.taffybear.budget.tx;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UnidentifiableTransactionsTest {

    @Test
    public void equals() {
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.of(Collections.singletonList(UnidentifiableTransaction
                .createUsdTransaction("merchant", LocalDate.of(2020, 12, 1),
                        BigDecimal.valueOf(2.5), "category", TransactionType.SALE)));
        final UnidentifiableTransactions other = UnidentifiableTransactions.of(Collections.singletonList(UnidentifiableTransaction
                .createUsdTransaction("merchant", LocalDate.of(2020, 12, 1),
                        BigDecimal.valueOf(2.5), "category", TransactionType.SALE)));

        assertThat(unidentifiableTransactions, is(other));
    }

    @Test
    public void hash() {
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.of(Collections.singletonList(UnidentifiableTransaction
                .createUsdTransaction("merchant", LocalDate.of(2020, 12, 1),
                        BigDecimal.valueOf(2.5), "category", TransactionType.SALE)));
        final UnidentifiableTransactions other = UnidentifiableTransactions.of(Collections.singletonList(UnidentifiableTransaction
                .createUsdTransaction("merchant", LocalDate.of(2020, 12, 1),
                        BigDecimal.valueOf(2.5), "category", TransactionType.SALE)));

        assertThat(unidentifiableTransactions.hashCode(), is(other.hashCode()));
    }

    @Test
    public void parseFromFile() throws URISyntaxException, IOException {
        // given
        final File file = new File(getClass().getResource("transactions.csv").toURI());

        // when
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.fromFile(file, true);

        // then
        final UnidentifiableTransaction t0 = UnidentifiableTransaction.createUsdTransaction("FIRSTENERGY/EZPAYRECUR",
                LocalDate.of(2020, 3, 30),
                BigDecimal.valueOf(-66.28),
                "Bills & Utilities",
                TransactionType.SALE);
        final UnidentifiableTransaction t1 = UnidentifiableTransaction.createUsdTransaction("CHEWY.COM",
                LocalDate.of(2020, 3, 30),
                BigDecimal.valueOf(35.28),
                "Shopping",
                TransactionType.RETURN);
        final UnidentifiableTransaction t2 = UnidentifiableTransaction.createUsdTransaction("VONEIFF OIL",
                LocalDate.of(2020, 3, 23),
                BigDecimal.valueOf(-344.85),
                "Home",
                TransactionType.SALE);
        assertThat(unidentifiableTransactions, is(UnidentifiableTransactions.of(ImmutableList.of(t0, t1, t2))));
    }

    @Test
    public void fromInputStream() throws IOException, URISyntaxException {
        // given

        // when
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.fromInputStream(new FileInputStream(new File(getClass()
                .getResource("transactions.csv").toURI())), true);

        // then
        final UnidentifiableTransaction t0 = UnidentifiableTransaction.createUsdTransaction("FIRSTENERGY/EZPAYRECUR",
                LocalDate.of(2020, 3, 30),
                BigDecimal.valueOf(-66.28),
                "Bills & Utilities",
                TransactionType.SALE);
        final UnidentifiableTransaction t1 = UnidentifiableTransaction.createUsdTransaction("CHEWY.COM",
                LocalDate.of(2020, 3, 30),
                BigDecimal.valueOf(35.28),
                "Shopping",
                TransactionType.RETURN);
        final UnidentifiableTransaction t2 = UnidentifiableTransaction.createUsdTransaction("VONEIFF OIL",
                LocalDate.of(2020, 3, 23),
                BigDecimal.valueOf(-344.85),
                "Home",
                TransactionType.SALE);
        assertThat(unidentifiableTransactions, is(UnidentifiableTransactions.of(ImmutableList.of(t0, t1, t2))));
    }

    @Test
    public void asInputStream() throws IOException {
        // given
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.of(Collections.singletonList(UnidentifiableTransaction
                .createUsdTransaction("merchant", LocalDate.of(2020, 12, 1),
                        BigDecimal.valueOf(2.5), "category", TransactionType.SALE)));

        // when
        final InputStream actual = unidentifiableTransactions.asInputStream();
        final UnidentifiableTransactions fromInputStream = UnidentifiableTransactions.fromInputStream(actual, false);

        // then
        assertThat(fromInputStream, is(unidentifiableTransactions));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void asList_immutable() {
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions.of(Collections.singletonList(UnidentifiableTransaction
                .createUsdTransaction("merchant", LocalDate.of(2020, 12, 1),
                        BigDecimal.valueOf(2.5), "category", TransactionType.SALE)));

        unidentifiableTransactions.asList().clear();
    }
}