package com.wingmar.taffybear.budget.tx;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class Transactions {

    private final Set<Transaction> transactions;

    private Transactions(Iterable<Transaction> transactions) {
        this.transactions = ImmutableSet.copyOf(transactions);
    }

    private Transactions(Transaction... transactions) {
        this(Arrays.asList(transactions));
    }

    public static Transactions of(Iterable<Transaction> transactions) {
        return new Transactions(transactions);
    }

    public static Transactions of(Transaction... transactions) {
        return new Transactions(transactions);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(transactions)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Transactions)) {
            return false;
        }

        final Transactions o = (Transactions) obj;
        return Objects.equals(transactions, o.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactions);
    }
}
