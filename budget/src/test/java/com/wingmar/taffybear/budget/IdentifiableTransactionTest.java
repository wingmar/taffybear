package com.wingmar.taffybear.budget;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IdentifiableTransactionTest {

    private final TestDataGenerator dataGenerator = TestDataGenerator.newInstance();

    @Test
    public void equals() {
        final IdentifiableTransaction transaction = dataGenerator.randomIdentifiableTransaction();
        final IdentifiableTransaction other = IdentifiableTransaction.create(transaction);

        assertThat(transaction, is(other));
    }

    @Test
    public void hash() {
        final IdentifiableTransaction transaction = dataGenerator.randomIdentifiableTransaction();
        final IdentifiableTransaction other = IdentifiableTransaction.create(transaction);

        assertThat(transaction.hashCode(), is(other.hashCode()));
    }
}