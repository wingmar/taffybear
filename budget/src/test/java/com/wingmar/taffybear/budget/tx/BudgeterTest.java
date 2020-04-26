package com.wingmar.taffybear.budget.tx;

import com.wingmar.taffybear.budget.TestBudgetApplicationContext;
import org.hamcrest.Matchers;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestBudgetApplicationContext.class)
public class BudgeterTest {

    @Autowired
    private TransactionService transactionService;

    private Budgeter budgeter;

    @Before
    public void before() {
        budgeter = Budgeter.create(transactionService);
    }

    @Test
    public void run() throws URISyntaxException, IOException {
        // given
        assertTrue(transactionService.all().isEmpty());

        final File file = new File(getClass().getResource("transactions.csv")
                .toURI());

        // when
        budgeter.run(file, true);

        // then
        final Transactions all = transactionService.all();
        all.forEach(t -> assertThat(t.getId(), not(nullValue())));

        final UnidentifiableTransaction t0 = UnidentifiableTransaction.createTransaction(
                Merchant.named("FIRSTENERGY/EZPAYRECUR"),
                LocalDate.of(2020, 3, 30),
                Money.of(CurrencyUnit.USD, BigDecimal.valueOf(-66.28).setScale(2, RoundingMode.HALF_UP)),
                Category.named("Bills & Utilities"),
                TransactionType.SALE
        );
        final UnidentifiableTransaction t1 = UnidentifiableTransaction.createTransaction(
                Merchant.named("CHEWY.COM"),
                LocalDate.of(2020, 3, 30),
                Money.of(CurrencyUnit.USD, BigDecimal.valueOf(35.28).setScale(2, RoundingMode.HALF_UP)),
                Category.named("Shopping"),
                TransactionType.RETURN
        );
        final UnidentifiableTransaction t2 = UnidentifiableTransaction.createTransaction(
                Merchant.named("VONEIFF OIL"), LocalDate.of(2020, 3, 23),
                Money.of(CurrencyUnit.USD, BigDecimal.valueOf(-344.85).setScale(2, RoundingMode.HALF_UP)),
                Category.named("Home"),
                TransactionType.SALE
        );
        assertThat(all.toUnidentifiableTransactions(), Matchers
                .is(UnidentifiableTransactions.of(Arrays.asList(t0, t1, t2))));
    }
}
