package com.wingmar.taffybear.budget;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class Transactions {
    private final Collection<Transaction> transactions;

    private Transactions(Collection<Transaction> transactions) {
        this.transactions = transactions;
    }

    public static Transactions createFromList(Collection<Transaction> transactions) {
        return new Transactions(transactions);
    }

    public static Transactions parseFromFile(File file) throws IOException {
        try (final CSVParser parser = CSVFormat.EXCEL
                .withFirstRecordAsHeader()
                .parse(new FileReader(file))) {
            final List<CSVRecord> records = parser.getRecords();
            return createFromList(records.stream().map(record -> Transaction
                    .createUsdTransaction(record.get(Header.DESCRIPTION.getName()),
                            LocalDate.parse(record.get(Header.TRANSACTION_DATE.getName()),
                                    DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                            BigDecimal.valueOf(Double.parseDouble(record.get(Header.AMOUNT.getName()))),
                            record.get(Header.CATEGORY.getName()),
                            TransactionType.fromName(record.get(Header.TYPE.getName()))))
                    .collect(Collectors.toList()));
        }
    }

    private enum Header {
        TRANSACTION_DATE ("Transaction Date"),
        DESCRIPTION ("Description"),
        CATEGORY ("Category"),
        TYPE ("Type"),
        AMOUNT ("Amount");

        private final String name;

        Header(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public List<Transaction> asList() {
        return ImmutableList.copyOf(transactions);
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(transactions)
                .toString();
    }
}
