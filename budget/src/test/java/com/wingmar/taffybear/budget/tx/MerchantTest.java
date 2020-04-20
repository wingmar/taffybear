package com.wingmar.taffybear.budget.tx;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class MerchantTest {

    private final TestDataGenerator generator = TestDataGenerator.newInstance();

    @Test
    public void equals() {
        final Merchant merchant = generator.randomMerchant();
        final Merchant other = Merchant.named(merchant.getName());

        assertThat(merchant, is(other));
    }

    @Test
    public void equals_diffName() {
        final Merchant merchant = generator.randomMerchant();
        final Merchant other = Merchant.named(merchant.getName() + "other");

        assertThat(merchant, is(not(other)));
    }

    @Test
    public void equals_diffObj() {
        final Merchant merchant = generator.randomMerchant();

        assertThat(merchant, is(not(merchant.getName())));
    }

    @Test
    public void equals_null() {
        final Merchant merchant = generator.randomMerchant();

        assertThat(merchant, not(equalTo(null)));
    }

    @Test
    public void hash() {
        final Merchant merchant = generator.randomMerchant();
        final Merchant other = Merchant.named(merchant.getName());

        assertThat(merchant.hashCode(), is(other.hashCode()));
    }

    @Test
    public void hash_diffName() {
        final Merchant merchant = generator.randomMerchant();
        final Merchant other = Merchant.named(merchant.getName() + "other");

        assertThat(merchant.hashCode(), is(not(other.hashCode())));
    }
}