package com.wingmar.taffybear.budget.tx;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Transactions {

    private final Set<Transaction> transactions;

    private Transactions(Iterable<Transaction> transactions) {
        this.transactions = transactions == null ? Collections.emptySet() : ImmutableSet.copyOf(transactions);
    }

    private Transactions(Transaction... transactions) {
        this(Arrays.asList(transactions));
    }

    private Transactions() {
        this(Collections.emptySet());
    }

    static Transactions ofIterable(Iterable<Transaction> transactions) {
        return new Transactions(transactions);
    }

    public static Transactions of(Transaction... transactions) {
        return new Transactions(transactions);
    }

    static Transactions empty() {
        return new Transactions();
    }

    boolean isEmpty() {
        return transactions.isEmpty();
    }

    Range<LocalDate> getDateRange() {
        if (isEmpty()) {
            return Range.openClosed(LocalDate.MIN, LocalDate.MIN);
        }

        final List<LocalDate> dates = transactions.stream().map(Transaction::getDate).collect(Collectors.toList());
        return Range.closed(Collections.min(dates), Collections.max(dates));
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
        return EqualsHelper.elementPairsAreEqual(transactions, o.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactions);
    }
}
