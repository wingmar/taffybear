package com.wingmar.taffybear.budget.tx;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryTransactionDao implements TransactionDao {

    private final Map<UUID, Transaction> transactions;
    private final Multimap<String, UUID> uploads;

    private InMemoryTransactionDao(Map<UUID, Transaction> transactions, Multimap<String, UUID> uploads) {
        this.transactions = transactions;
        this.uploads = uploads;
    }

    private InMemoryTransactionDao() {
        this(new HashMap<>(), ArrayListMultimap.create());
    }

    public static InMemoryTransactionDao newInstance() {
        return new InMemoryTransactionDao();
    }

    @Override
    public Transaction insert(UnidentifiableTransaction unidentifiableTransaction) {
        final Transaction transaction = Transaction.create(UUID.randomUUID(), unidentifiableTransaction);
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public boolean logUpload(UUID transactionId, String filename) {
        return transactions.containsKey(transactionId) && uploads.put(filename, transactionId);
    }

    @Override
    public List<Transaction> all() {
        return ImmutableList.copyOf(transactions.values());
    }

    @Override
    public Optional<Transaction> find(UUID id) {
        return Optional.ofNullable(transactions.get(id));
    }

    @Override
    public List<Transaction> find(LocalDate lower, LocalDate upper) {
        return ImmutableList.copyOf(all().stream().filter(transaction -> {
            final LocalDate date = transaction.getDate();
            return (date.isEqual(lower) || date.isAfter(lower)) &&
                    (date.isEqual(upper) || date.isBefore(upper));
        }).collect(Collectors.toList()));
    }

    @VisibleForTesting
    Multimap<String, UUID> getUploads() {
        return uploads;
    }
}
