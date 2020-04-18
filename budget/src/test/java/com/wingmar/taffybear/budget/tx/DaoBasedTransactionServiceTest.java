package com.wingmar.taffybear.budget.tx;

import com.google.common.collect.Range;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DaoBasedTransactionServiceTest {

    private final TestDataGenerator generator = TestDataGenerator.newInstance();

    private TransactionService transactionService;

    @Mock
    private TransactionDao transactionDao;

    @Before
    public void before() {
        transactionService = new DaoBasedTransactionService(transactionDao);
    }

    @Test
    public void find() {
        final Range<LocalDate> window = Range.greaterThan(LocalDate.now());
        final List<Transaction> transactionsList = Collections.singletonList(generator.randomTransaction());
        Mockito.when(transactionDao.find(window)).thenReturn(transactionsList);

        final Transactions transactions = transactionService.find(window);

        assertThat(transactions, is(Transactions.ofIterable(transactionsList)));
    }

    @Test
    public void find_empty() {
        final Range<LocalDate> window = Range.greaterThan(LocalDate.now());
        Mockito.when(transactionDao.find(window)).thenReturn(Collections.emptyList());

        final Transactions transactions = transactionService.find(window);

        assertTrue(transactions.isEmpty());
    }
}