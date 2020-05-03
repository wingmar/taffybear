package com.wingmar.bk9;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Transactions {

    private final Set<Income> incomes;
    private final Set<Expense> expenses;
    private final Set<Withdrawal> withdrawals;
    private final Set<Transaction> transactions;

    private Transactions(Set<Income> incomes, Set<Expense> expenses, Set<Withdrawal> withdrawals) {
        this.incomes = ImmutableSet.copyOf(incomes);
        this.expenses = ImmutableSet.copyOf(expenses);
        this.withdrawals = ImmutableSet.copyOf(withdrawals);
        final Set<Transaction> transactions = new HashSet<>();
        transactions.addAll(incomes);
        transactions.addAll(expenses);
        transactions.addAll(withdrawals);
        this.transactions = ImmutableSet.copyOf(transactions);
    }

    static Transactions incomes(Income... incomes) {
        return new Transactions(ImmutableSet.copyOf(incomes), Collections.emptySet(), Collections.emptySet());
    }

    @VisibleForTesting
    static Transactions of(Set<Income> incomes, Set<Expense> expenses, Set<Withdrawal> withdrawals) {
        return new Transactions(incomes, expenses, withdrawals);
    }

    static Transactions fromFile(File file) throws IOException {
        return readTransactions(new FileReader(file));
    }

    private static TransactionRecord fromCsvRecord(CSVRecord record) {
        final String dateStr = record.get(Header.DATE.getName());
        Preconditions.checkNotNull(dateStr);
        final String note = record.get(Header.NOTE.getName());
        Preconditions.checkNotNull(note);
        final String paymentTypeStr = record.get(Header.PAYMENT_TYPE.getName());
        Preconditions.checkNotNull(paymentTypeStr);

        final LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("M/d/yyyy"));
        final String incomeStr = record.get(Header.INCOME.getName());
        final String expensesStr = record.get(Header.EXPENSES.getName());
        final String withdrawalsStr = record.get(Header.WITHDRAWALS.getName());
        return new TransactionRecord(date, incomeStr, expensesStr, withdrawalsStr, note, "Cash".equals(paymentTypeStr));
    }

    private static <T extends Transaction> Set<T> fromTransactionRecords(List<TransactionRecord> transactionRecords,
                                                                         Function<TransactionRecord, Optional<T>> creator) {
        return transactionRecords.stream().map(creator).filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toSet());
    }

    private static Transactions parseRecordsWithHeaders(List<CSVRecord> records) {
        final List<TransactionRecord> transactionRecords = records.stream().map(Transactions::fromCsvRecord)
                .collect(Collectors.toList());
        final Set<Income> incomes = fromTransactionRecords(transactionRecords, TransactionRecord::toIncome);
        final Set<Expense> expenses = fromTransactionRecords(transactionRecords, TransactionRecord::toExpense);
        final Set<Withdrawal> withdrawals = fromTransactionRecords(transactionRecords, TransactionRecord::toWithdrawal);
        return of(incomes, expenses, withdrawals);
    }

    private static Transactions readTransactions(Reader reader) throws IOException {
//        if (includeHeaders) {
        try (final CSVParser parser = CSVFormat.newFormat('\t').withFirstRecordAsHeader().parse(reader)) {
            final List<CSVRecord> records = parser.getRecords();
            return parseRecordsWithHeaders(records);
        }
//        } else {
//            try (final CSVParser parser = CSVFormat.EXCEL.parse(reader)) {
//                final List<CSVRecord> records = parser.getRecords();
//                return parseRecordsWithoutHeaders(records);
//            }
//        }
    }

    Set<Income> incomes() {
        return incomes;
    }

    Set<Expense> expenses() {
        return expenses;
    }

    Set<Withdrawal> withdrawals() {
        return withdrawals;
    }

    static class TransactionRecord {
        private final LocalDate date;
        private final String income, expense, withdrawal, note;
        private final boolean cash;

        TransactionRecord(LocalDate date, String income, String expense, String withdrawal, String note, boolean cash) {
            this.date = date;
            this.income = income;
            this.expense = expense;
            this.withdrawal = withdrawal;
            this.note = note;
            this.cash = cash;
        }

        Optional<Income> toIncome() {
            if (Strings.isNullOrEmpty(income)) {
                return Optional.empty();
            }

            return Optional.of(Income.unidentified(date, amountOf(income), note, cash));
        }

        Optional<Expense> toExpense() {
            if (Strings.isNullOrEmpty(expense)) {
                return Optional.empty();
            }

            return Optional.of(Expense.unidentified(date, amountOf(expense), note));
        }

        Optional<Withdrawal> toWithdrawal() {
            if (Strings.isNullOrEmpty(withdrawal)) {
                return Optional.empty();
            }

            return Optional.of(Withdrawal.unidentified(date, amountOf(withdrawal), note));
        }
    }

    private static BigDecimal amountOf(String moneyStr) {
        return new BigDecimal(moneyStr.replace("$", "").replace(",", "").trim());
    }

    private enum Header {
        DATE("Date"),
        PAYMENT_TYPE("Payment Type"),
        INCOME("Income"),
        EXPENSES("Expenses"),
        WITHDRAWALS("Withdrawals"),
        NOTE("Note");

        private final String name;

        Header(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        final Transactions other = (Transactions) obj;
        return EqualsHelper.newInstance().elementPairsAreEqual(transactions, other.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactions);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(transactions)
                .toString();
    }
}
