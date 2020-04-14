package com.wingmar.taffybear.budget;

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

public class TransactionsTest {

    @Test
    public void equals() {
        final Transactions transactions = Transactions.of(Collections.singletonList(Transaction
                .createUsdTransaction("merchant", LocalDate.of(2020, 12, 1),
                        BigDecimal.valueOf(2.5), "category", TransactionType.SALE)));
        final Transactions other = Transactions.of(Collections.singletonList(Transaction
                .createUsdTransaction("merchant", LocalDate.of(2020, 12, 1),
                        BigDecimal.valueOf(2.5), "category", TransactionType.SALE)));

        assertThat(transactions, is(other));
    }

    @Test
    public void hash() {
        final Transactions transactions = Transactions.of(Collections.singletonList(Transaction
                .createUsdTransaction("merchant", LocalDate.of(2020, 12, 1),
                        BigDecimal.valueOf(2.5), "category", TransactionType.SALE)));
        final Transactions other = Transactions.of(Collections.singletonList(Transaction
                .createUsdTransaction("merchant", LocalDate.of(2020, 12, 1),
                        BigDecimal.valueOf(2.5), "category", TransactionType.SALE)));

        assertThat(transactions.hashCode(), is(other.hashCode()));
    }

    @Test
    public void parseFromFile() throws URISyntaxException, IOException {
        // given
        final File file = new File(getClass().getResource("transactions.csv").toURI());

        // when
        final Transactions transactions = Transactions.fromFile(file, true);

        // then
        final Transaction t0 = Transaction.createUsdTransaction("FIRSTENERGY/EZPAYRECUR",
                LocalDate.of(2020, 3, 30),
                BigDecimal.valueOf(-66.28),
                "Bills & Utilities",
                TransactionType.SALE);
        final Transaction t1 = Transaction.createUsdTransaction("CHEWY.COM",
                LocalDate.of(2020, 3, 30),
                BigDecimal.valueOf(35.28),
                "Shopping",
                TransactionType.RETURN);
        final Transaction t2 = Transaction.createUsdTransaction("VONEIFF OIL",
                LocalDate.of(2020, 3, 23),
                BigDecimal.valueOf(-344.85),
                "Home",
                TransactionType.SALE);
        assertThat(transactions, is(Transactions.of(ImmutableList.of(t0, t1, t2))));
    }

    @Test
    public void fromInputStream() throws IOException, URISyntaxException {
        // given

        // when
        final Transactions transactions = Transactions.fromInputStream(new FileInputStream(new File(getClass()
                .getResource("transactions.csv").toURI())), true);

        // then
        final Transaction t0 = Transaction.createUsdTransaction("FIRSTENERGY/EZPAYRECUR",
                LocalDate.of(2020, 3, 30),
                BigDecimal.valueOf(-66.28),
                "Bills & Utilities",
                TransactionType.SALE);
        final Transaction t1 = Transaction.createUsdTransaction("CHEWY.COM",
                LocalDate.of(2020, 3, 30),
                BigDecimal.valueOf(35.28),
                "Shopping",
                TransactionType.RETURN);
        final Transaction t2 = Transaction.createUsdTransaction("VONEIFF OIL",
                LocalDate.of(2020, 3, 23),
                BigDecimal.valueOf(-344.85),
                "Home",
                TransactionType.SALE);
        assertThat(transactions, is(Transactions.of(ImmutableList.of(t0, t1, t2))));
    }

    @Test
    public void asInputStream() throws IOException {
        // given
        final Transactions transactions = Transactions.of(Collections.singletonList(Transaction
                .createUsdTransaction("merchant", LocalDate.of(2020, 12, 1),
                        BigDecimal.valueOf(2.5), "category", TransactionType.SALE)));

        // when
        final InputStream actual = transactions.asInputStream();
        final Transactions fromInputStream = Transactions.fromInputStream(actual, false);

        // then
        assertThat(fromInputStream, is(transactions));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void asList_immutable() {
        final Transactions transactions = Transactions.of(Collections.singletonList(Transaction
                .createUsdTransaction("merchant", LocalDate.of(2020, 12, 1),
                        BigDecimal.valueOf(2.5), "category", TransactionType.SALE)));

        transactions.asList().clear();
    }
}