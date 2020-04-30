package com.wingmar.bk9;

import org.junit.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExpenseTest {

    @Test
    public void create() throws Exception {
        final UUID id = UUID.randomUUID();
        final Expense expense = Expense.create(id, LocalDate.parse("2020-02-01"), BigDecimal.valueOf(1234), "note");
        final Expense other = Expense.create(id, LocalDate.parse("2020-02-01"), BigDecimal.valueOf(1234), "note");

        assertThat(expense, is(other));
    }

    @Test
    public void unidentified() throws Exception {
        final Expense expense = Expense.unidentified(LocalDate.parse("2020-02-01"), BigDecimal.valueOf(1234), "note");
        final Expense other = Expense.create(null, LocalDate.parse("2020-02-01"), BigDecimal.valueOf(1234), "note");

        assertThat(expense, is(other));
    }
}