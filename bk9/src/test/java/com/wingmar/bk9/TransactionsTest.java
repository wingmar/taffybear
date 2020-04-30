package com.wingmar.bk9;

import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TransactionsTest {

    @Test
    public void fromFile() throws URISyntaxException, IOException {
        // given
        final File file = new File(getClass().getResource("transactions.tsv").toURI());

        // when
        final Transactions transactions = Transactions.fromFile(file);

        // then
        final Transactions expected = Transactions.of(
                Expense.unidentified(parseDate("1/2/2020"), amountOf("6.00"), "G-Suite"),
                Expense.unidentified(parseDate("1/2/2020"), amountOf("16.00"), "Square Space"),
                Expense.unidentified(parseDate("1/4/2020"), amountOf("31.80"), "Tractor Supply - Mats"),
                Income.unidentified(parseDate("1/5/2020"), amountOf("500.00"), "Jolene Deposit", false),
                Expense.unidentified(parseDate("1/5/2020"), amountOf("14.80"), "Square % for Jolene"),
                Income.unidentified(parseDate("1/5/2020"), amountOf("75.00"), "Gunnar Eval", false),
                Expense.unidentified(parseDate("1/5/2020"), amountOf("2.48"), "Square % for Gunnar"),
                Expense.unidentified(parseDate("1/5/2020"), amountOf("76.00"), "E Collar Tech Order"),
                Expense.unidentified(parseDate("1/7/2020"), amountOf("232.00"), "E Collar Tech Order"),
                Income.unidentified(parseDate("1/9/2020"), amountOf("840.00"), "Mitch - Camp Balance", false),
                Income.unidentified(parseDate("1/17/2020"), amountOf("360.00"), "Bella Boarding", false),
                Income.unidentified(parseDate("1/21/2020"), amountOf("300.00"), "Quinley 5 Lesson Package", true),
                Income.unidentified(parseDate("1/21/2020"), amountOf("360.00"), "Dori - Camp Deposit", false),
                Income.unidentified(parseDate("1/27/2020"), amountOf("1259.00"), "Jolene Balance", false),
                Income.unidentified(parseDate("1/28/2020"), amountOf("420.00"), "Dori - Camp Payment #1", false),
                Withdrawal.unidentified(parseDate("1/28/2020"), amountOf("2500.00"), "Owner's Draw")
        );

        assertThat(transactions.asSet(), is(expected.asSet()));
    }

    private BigDecimal amountOf(String val) {
        return new BigDecimal(val);
    }

    private LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("M/d/yyyy"));
    }
}