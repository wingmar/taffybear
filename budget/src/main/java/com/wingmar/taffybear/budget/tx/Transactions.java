package com.wingmar.taffybear.budget.tx;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
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

    int size() {
        return transactions.size();
    }

    boolean isEmpty() {
        return toUnidentifiableTransactions().isEmpty();
    }

    void forEach(Consumer<Transaction> consumer) {
        transactions.forEach(consumer);
    }

    Range<LocalDate> getDateRange() {
        return toUnidentifiableTransactions().getDateRange();
    }

    UnidentifiableTransactions toUnidentifiableTransactions() {
        return UnidentifiableTransactions.of(transactions.stream().map(Transaction::incognito)
                .collect(Collectors.toList()));
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
