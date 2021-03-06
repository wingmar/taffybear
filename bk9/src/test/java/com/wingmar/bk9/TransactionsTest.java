package com.wingmar.bk9;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

public class TransactionsTest {

    @Test
    public void equals() {
        final UUID id = UUID.randomUUID();
        final Transactions transactions = Transactions
                .incomes(Income.create(id, LocalDate.parse("1923-03-05"), BigDecimal.valueOf(132), "note", true));
        final Transactions other = Transactions
                .incomes(Income.create(id, LocalDate.parse("1923-03-05"), BigDecimal.valueOf(132), "note", true));

        assertThat(transactions, is(other));
    }

    @Test
    public void equals_null() {
        final UUID id = UUID.randomUUID();
        final Transactions transactions = Transactions
                .incomes(Income.create(id, LocalDate.parse("1923-03-05"), BigDecimal.valueOf(132), "note", true));

        assertNotEquals(null, transactions);
    }

    @Test
    public void equals_diffObj() {
        final UUID id = UUID.randomUUID();
        final Transactions transactions = Transactions
                .incomes(Income.create(id, LocalDate.parse("1923-03-05"), BigDecimal.valueOf(132), "note", true));

        assertNotEquals(transactions, new Object());
    }

    @Test
    public void hash() {
        final UUID id = UUID.randomUUID();
        final Transactions transactions = Transactions
                .incomes(Income.create(id, LocalDate.parse("1923-03-05"), BigDecimal.valueOf(132), "note", true));
        final Transactions other = Transactions
                .incomes(Income.create(id, LocalDate.parse("1923-03-05"), BigDecimal.valueOf(132), "note", true));

        assertThat(transactions.hashCode(), is(other.hashCode()));
    }

    @Test
    public void fromFile() throws URISyntaxException, IOException {
        // given
        final File file = new File(getClass().getResource("transactions.tsv").toURI());

        // when
        final Transactions transactions = Transactions.fromFile(file);

        // then
        final Set<Income> incomes = ImmutableSet.of(
                Income.unidentified(parseDate("1/5/2020"), amountOf("500.00"), "Jolene Deposit", false),
                Income.unidentified(parseDate("1/5/2020"), amountOf("75.00"), "Gunnar Eval", false),
                Income.unidentified(parseDate("1/9/2020"), amountOf("840.00"), "Mitch - Camp Balance", false),
                Income.unidentified(parseDate("1/17/2020"), amountOf("360.00"), "Bella Boarding", false),
                Income.unidentified(parseDate("1/21/2020"), amountOf("300.00"), "Quinley 5 Lesson Package", true),
                Income.unidentified(parseDate("1/21/2020"), amountOf("360.00"), "Dori - Camp Deposit", false),
                Income.unidentified(parseDate("1/27/2020"), amountOf("1259.00"), "Jolene Balance", false),
                Income.unidentified(parseDate("1/28/2020"), amountOf("420.00"), "Dori - Camp Payment #1", false)
        );

        final Set<Expense> expenses = ImmutableSet.of(
                Expense.unidentified(parseDate("1/2/2020"), amountOf("6.00"), "G-Suite"),
                Expense.unidentified(parseDate("1/2/2020"), amountOf("16.00"), "Square Space"),
                Expense.unidentified(parseDate("1/4/2020"), amountOf("31.80"), "Tractor Supply - Mats"),
                Expense.unidentified(parseDate("1/5/2020"), amountOf("14.80"), "Square % for Jolene"),
                Expense.unidentified(parseDate("1/5/2020"), amountOf("2.48"), "Square % for Gunnar"),
                Expense.unidentified(parseDate("1/5/2020"), amountOf("76.00"), "E Collar Tech Order"),
                Expense.unidentified(parseDate("1/7/2020"), amountOf("232.00"), "E Collar Tech Order")
        );

        final Set<Withdrawal> withdrawals = ImmutableSet
                .of(Withdrawal.unidentified(parseDate("1/28/2020"), amountOf("2500.00"), "Owner's Draw"));

        final Transactions expected = Transactions.of(incomes, expenses, withdrawals);

        assertThat(transactions, is(expected));
    }

    private BigDecimal amountOf(String val) {
        return new BigDecimal(val);
    }

    private LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("M/d/yyyy"));
    }

    @Test
    public void testToString() {
        final UUID id = UUID.randomUUID();
        final Transactions transactions = Transactions
                .incomes(Income.create(id, LocalDate.parse("1923-03-05"), BigDecimal.valueOf(132), "note", true));

        final String actual = transactions.toString();

        assertThat(actual, is("Transactions{[Income{" + id + ", 1923-03-05, 132, note, true}]}"));
    }

    @Test
    public void incomes() {
        // given

        // when
        final Transactions transactions = Transactions
                .incomes(Income.unidentified(LocalDate.now(), BigDecimal.ONE, "note", true));

        // then
        assertThat(transactions.incomes(), is(ImmutableSet
                .of(Income.unidentified(LocalDate.now(), BigDecimal.ONE, "note", true))));
    }

    @Test
    public void incomes_expensesEmpty() {
        // given

        // when
        final Transactions transactions = Transactions
                .incomes(Income.unidentified(LocalDate.now(), BigDecimal.ONE, "note", true));

        // then
        assertThat(transactions.expenses(), is(Collections.emptySet()));
    }

    @Test
    public void incomes_withdrawalsEmpty() {
        // given

        // when
        final Transactions transactions = Transactions
                .incomes(Income.unidentified(LocalDate.now(), BigDecimal.ONE, "note", true));

        // then
        assertThat(transactions.withdrawals(), is(Collections.emptySet()));
    }
}