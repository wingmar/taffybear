package com.wingmar.taffybear.budget;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class Transactions {
    private final Collection<UnidentifiableTransaction> unidentifiableTransactions;

    private Transactions(Collection<UnidentifiableTransaction> unidentifiableTransactions) {
        this.unidentifiableTransactions = unidentifiableTransactions;
    }

    static Transactions of(Collection<UnidentifiableTransaction> unidentifiableTransactions) {
        return new Transactions(unidentifiableTransactions);
    }

    static Transactions fromInputStream(InputStream inputStream, boolean includeHeaders) throws IOException {
        return readTransactions(new InputStreamReader(inputStream), includeHeaders);
    }

    static Transactions fromFile(File file, boolean includeHeaders) throws IOException {
        return readTransactions(new FileReader(file), includeHeaders);
    }

    private static Transactions readTransactions(Reader reader, boolean includeHeaders) throws IOException {
        if (includeHeaders) {
            try (final CSVParser parser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader)) {
                final List<CSVRecord> records = parser.getRecords();
                return parseRecordsWithHeaders(records);
            }
        } else {
            try (final CSVParser parser = CSVFormat.EXCEL.parse(reader)) {
                final List<CSVRecord> records = parser.getRecords();
                return parseRecordsWithoutHeaders(records);
            }
        }
    }

    private static Transactions parseRecordsWithHeaders(List<CSVRecord> records) {
        return of(records.stream().map(record -> UnidentifiableTransaction
                .createUsdTransaction(record.get(Header.DESCRIPTION.getName()),
                        LocalDate.parse(record.get(Header.TRANSACTION_DATE.getName()),
                                DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                        BigDecimal.valueOf(Double.parseDouble(record.get(Header.AMOUNT.getName()))),
                        record.get(Header.CATEGORY.getName()), TransactionType.fromName(record.get(Header.TYPE.getName()))))
                .collect(Collectors.toList()));
    }

    private static Transactions parseRecordsWithoutHeaders(List<CSVRecord> records) {
        final List<UnidentifiableTransaction> list = records.stream().map(record -> {
            final String merchant = record.get(Header.DESCRIPTION.ordinal());
            final LocalDate transactionDate = LocalDate.parse(record.get(Header.TRANSACTION_DATE.ordinal()),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            final BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(record.get(Header.AMOUNT.ordinal())));
            final String category = record.get(Header.CATEGORY.ordinal());
            final TransactionType type = TransactionType.fromName(record.get(Header.TYPE.ordinal()));
            return UnidentifiableTransaction
                    .createUsdTransaction(merchant,
                            transactionDate,
                            amount,
                            category, type);
        })
                .collect(Collectors.toList());
        return of(list);

    }

    InputStream asInputStream() throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(out), CSVFormat.EXCEL)) {
            unidentifiableTransactions.forEach(transaction -> {
                try {
                    printer.printRecord(transaction.getDate(),
                            transaction.getMerchant(),
                            transaction.getCategory(),
                            transaction.getType().getName(),
                            transaction.getAmount().getAmount());
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            });
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    List<UnidentifiableTransaction> asList() {
        return ImmutableList.copyOf(unidentifiableTransactions);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Transactions)) {
            return false;
        }

        final Transactions o = (Transactions) obj;
        return Objects.equals(unidentifiableTransactions, o.unidentifiableTransactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unidentifiableTransactions);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(unidentifiableTransactions)
                .toString();
    }

    private enum Header {
        TRANSACTION_DATE("UnidentifiableTransaction Date"),
        DESCRIPTION("Description"),
        CATEGORY("Category"),
        TYPE("Type"),
        AMOUNT("Amount");

        private final String name;

        Header(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }
}
