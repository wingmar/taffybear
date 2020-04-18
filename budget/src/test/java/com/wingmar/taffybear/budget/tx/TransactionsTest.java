package com.wingmar.taffybear.budget.tx;

import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;

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
}