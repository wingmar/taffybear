package com.wingmar.taffybear.budget.tx;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class CategoryTest {

    private final TestDataGenerator generator = TestDataGenerator.newInstance();

    @Test
    public void equals() {
        final Category merchant = generator.randomCategory();
        final Category other = Category.named(merchant.getName());

        assertThat(merchant, is(other));
    }

    @Test
    public void equals_diffName() {
        final Category merchant = generator.randomCategory();
        final Category other = Category.named(merchant.getName() + "other");

        assertThat(merchant, is(not(other)));
    }

    @Test
    public void equals_diffObj() {
        final Category merchant = generator.randomCategory();

        assertThat(merchant, is(not(merchant.getName())));
    }

    @Test
    public void equals_null() {
        final Category category = generator.randomCategory();

        assertThat(category, not(equalTo(null)));
    }

    @Test
    public void hash() {
        final Category merchant = generator.randomCategory();
        final Category other = Category.named(merchant.getName());

        assertThat(merchant.hashCode(), is(other.hashCode()));
    }

    @Test
    public void hash_diffName() {
        final Category merchant = generator.randomCategory();
        final Category other = Category.named(merchant.getName() + "other");

        assertThat(merchant.hashCode(), is(not(other.hashCode())));
    }
}