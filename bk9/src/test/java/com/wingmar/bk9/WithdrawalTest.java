package com.wingmar.bk9;

import org.junit.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WithdrawalTest {

    @Test
    public void unidentified() throws Exception {
        final Withdrawal withdrawal = Withdrawal.unidentified(LocalDate.parse("2020-01-01"), BigDecimal.valueOf(1234), "note");
        final Withdrawal other = Withdrawal.create(null, LocalDate.parse("2020-01-01"), BigDecimal.valueOf(1234), "note");

        assertThat(withdrawal, is(other));
    }
}
