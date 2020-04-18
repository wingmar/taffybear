package com.wingmar.taffybear.budget.tx;

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
        final List<Transaction> transactionsList = Collections.singletonList(generator.randomTransaction());
        Mockito.when(transactionDao.find(Mockito.any(), Mockito.any())).thenReturn(transactionsList);

        final Transactions transactions = transactionService.find(LocalDate.now(), LocalDate.now());

        assertThat(transactions, is(Transactions.ofIterable(transactionsList)));
    }

    @Test
    public void find_empty() {
        Mockito.when(transactionDao.find(Mockito.any(), Mockito.any())).thenReturn(Collections.emptyList());

        final Transactions transactions = transactionService.find(LocalDate.now(), LocalDate.now());

        assertTrue(transactions.isEmpty());
    }
}