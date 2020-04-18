package com.wingmar.taffybear.budget.tx;

import org.junit.Test;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
        final Transactions of = Transactions.of(Arrays.asList(t0, t1, t2));

        assertThat(of, is(transactions));
    }
}