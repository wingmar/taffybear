package com.wingmar.bk9;

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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Transactions {

    private final Set<Transaction> transactions;

    private Transactions(Set<Transaction> transactions) {
        this.transactions = ImmutableSet.copyOf(transactions);
    }

    static Transactions of(Transaction... transactions) {
        return new Transactions(ImmutableSet.copyOf(transactions));
    }

    static Transactions fromFile(File file) throws IOException {
        return readTransactions(new FileReader(file));
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

    private static Transactions parseRecordsWithHeaders(List<CSVRecord> records) {
        final Set<Transaction> collect = records.stream().map(record -> {
            final String dateStr = record.get(Header.DATE.getName());
            Preconditions.checkNotNull(dateStr);
            final String note = record.get(Header.NOTE.getName());
            Preconditions.checkNotNull(note);
            final String paymentTypeStr = record.get(Header.PAYMENT_TYPE.getName());
            Preconditions.checkNotNull(paymentTypeStr);

            final LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("M/d/yyyy"));
            final String incomeStr = record.get(Header.INCOME.getName());
            final String expensesStr = record.get(Header.EXPENSES.getName());
            if (!Strings.isNullOrEmpty(incomeStr)) {
                // income
                final BigDecimal amount = amountOf(incomeStr);
                return Income.create(null, date, amount, note, paymentTypeStr.equals("Cash"));
            } else if (!Strings.isNullOrEmpty(expensesStr)) {
                // expense
                return Expense.create(null, date, amountOf(expensesStr), note);
            } else {
                // withdrawal
                final String withdrawalsStr = record.get(Header.WITHDRAWALS.getName());
                return Withdrawal.create(null, date, amountOf(withdrawalsStr), note);
            }
        }).collect(Collectors.toSet());

        return new Transactions(collect);
    }

    private static BigDecimal amountOf(String moneyStr) {
        return new BigDecimal(moneyStr.replace("$", "").replace(",", "").trim());
    }

    Set<Transaction> asSet() {
        return transactions;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(transactions)
                .toString();
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
}
