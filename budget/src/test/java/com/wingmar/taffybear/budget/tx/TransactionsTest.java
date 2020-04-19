package com.wingmar.taffybear.budget.tx;

import com.google.common.collect.Range;
import org.junit.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TransactionsTest {

    private final TestDataGenerator generator = TestDataGenerator.newInstance();

    @Test
    public void equals() {
        final Transaction t0 = generator.randomTransaction();
        final Transaction t1 = generator.randomTransaction();
        final Transaction t2 = generator.randomTransaction();
        final Transactions transactions = Transactions.of(t0, t1, t2);
        final Transactions other = Transactions.of(t0, t1, t2);

        assertThat(transactions, is(other));
    }

    @Test
    public void equals_null() {
        final Transaction t0 = generator.randomTransaction();
        final Transaction t1 = generator.randomTransaction();
        final Transaction t2 = generator.randomTransaction();
        final Transactions transactions = Transactions.of(t0, t1, t2);
        assertFalse(transactions.equals(null));
    }

    @Test
    public void hash() {
        final Transaction t0 = generator.randomTransaction();
        final Transaction t1 = generator.randomTransaction();
        final Transaction t2 = generator.randomTransaction();
        final Transactions transactions = Transactions.of(t0, t1, t2);
        final Transactions other = Transactions.of(t0, t1, t2);

        assertThat(transactions.hashCode(), is(other.hashCode()));
    }

    @Test
    public void of_iterable() {
        final Transaction t0 = generator.randomTransaction();
        final Transaction t1 = generator.randomTransaction();
        final Transaction t2 = generator.randomTransaction();
        final Transactions transactions = Transactions.of(t0, t1, t2);
        final Transactions of = Transactions.ofIterable(Arrays.asList(t0, t1, t2));

        assertThat(of, is(transactions));
    }

    @Test
    public void of_empty() {
        final Transactions transactions = Transactions.of();

        assertThat(transactions, is(Transactions.ofIterable(Collections.emptyList())));
    }

    @Test
    public void isEmpty_true() {
        final Transactions transactions = Transactions.ofIterable(Collections.emptyList());

        assertTrue(transactions.isEmpty());
    }

    @Test
    public void isEmpty_false() {
        final Transactions transactions = Transactions.of(generator.randomTransaction());

        assertFalse(transactions.isEmpty());
    }

    @Test
    public void of_null() {
        final Transactions transactions = Transactions.ofIterable(null);

        assertTrue(transactions.isEmpty());
    }

    @Test
    public void empty() {
        final Transactions transactions = Transactions.empty();

        assertTrue(transactions.isEmpty());
    }

    @Test
    public void getDateRange() {
        final List<Transaction> iterable = IntStream.range(0, 100)
                .mapToObj(i -> generator.randomTransaction())
                .collect(Collectors.toList());

        final Transactions transactions = Transactions.ofIterable(iterable);

        final LocalDate min = iterable.stream().map(Transaction::getDate).sorted().collect(Collectors.toList()).get(0);
        final LocalDate max = iterable.stream().map(Transaction::getDate).sorted(Comparator.reverseOrder())
                .collect(Collectors.toList()).get(0);
        assertThat(transactions.getDateRange(), is(Range.closed(min, max)));
    }

    @Test
    public void getDateRange_containsAllDates() {
        // given
        final List<Transaction> iterable = IntStream.range(0, 100)
                .mapToObj(i -> generator.randomTransaction())
                .collect(Collectors.toList());
        final Transactions transactions = Transactions.ofIterable(iterable);

        // when
        final Range<LocalDate> dateRange = transactions.getDateRange();

        // then
        final List<LocalDate> dates = iterable.stream().map(Transaction::getDate).sorted().collect(Collectors.toList());
        assertTrue(dateRange.containsAll(dates));
    }

    @Test
    public void getDateRange_single() {
        final Transaction transaction = generator.randomTransaction();

        final Transactions transactions = Transactions.of(transaction);

        assertThat(transactions.getDateRange(), is(Range.singleton(transaction.getDate())));
    }

    @Test
    public void getDateRange_empty() {
        final Transactions empty = Transactions.empty();

        assertTrue(empty.getDateRange().isEmpty());
    }
}