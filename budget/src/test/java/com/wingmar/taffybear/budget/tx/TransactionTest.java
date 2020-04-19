package com.wingmar.taffybear.budget.tx;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class TransactionTest {

    private final TestDataGenerator dataGenerator = TestDataGenerator.newInstance();

    @Test
    public void equals() {
        final Transaction transaction = dataGenerator.randomTransaction();
        final Transaction other = Transaction.create(transaction);

        assertThat(transaction, is(other));
    }

    @Test
    public void equals_null() {
        assertFalse(dataGenerator.randomTransaction().equals(null));
    }

    @Test
    public void hash() {
        final Transaction transaction = dataGenerator.randomTransaction();
        final Transaction other = Transaction.create(transaction);

        assertThat(transaction.hashCode(), is(other.hashCode()));
    }
}