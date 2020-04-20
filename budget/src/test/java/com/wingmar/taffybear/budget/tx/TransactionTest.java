package com.wingmar.taffybear.budget.tx;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class TransactionTest {

    private final TestDataGenerator generator = TestDataGenerator.newInstance();

    @Test
    public void equals() {
        final Transaction transaction = generator.randomTransaction();
        final Transaction other = Transaction.create(transaction);

        assertThat(transaction, is(other));
    }

    @Test
    public void equals_null() {
        assertThat(generator.randomTransaction(), not(equalTo(null)));
    }

    @Test
    public void hash() {
        final Transaction transaction = generator.randomTransaction();
        final Transaction other = Transaction.create(transaction);

        assertThat(transaction.hashCode(), is(other.hashCode()));
    }

    @Test
    public void incognito() {
        final Transaction transaction = generator.randomTransaction();

        final UnidentifiableTransaction actual = transaction.incognito();

        assertThat(actual, is(UnidentifiableTransaction.createTransaction(transaction.getMerchant(),
                transaction.getDate(), transaction.getAmount(), transaction.getCategory(), transaction.getType())));
    }
}