package com.wingmar.taffybear.budget.tx;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
    public void all() {
        final List<Transaction> transactionsList = Collections.singletonList(generator.randomTransaction());
        Mockito.when(transactionDao.all()).thenReturn(transactionsList);

        final Transactions all = transactionService.all();

        assertThat(all, is(Transactions.ofIterable(transactionsList)));
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

    @Test
    public void save_noOverlap_insertsAll() {
        // given
        final Transaction t0 = generator.randomTransaction(LocalDate.parse("2020-01-01"));
        final Transaction t1 = generator.randomTransaction(LocalDate.parse("2020-02-01"));
        final Transaction t2 = generator.randomTransaction(LocalDate.parse("2020-03-01"));

        Mockito.when(transactionDao.find(Mockito.any(), Mockito.any())).thenReturn(Arrays.asList(t0, t1, t2));

        final UnidentifiableTransaction new0 = generator.randomUnidentifiableTransaction(LocalDate.parse("2019-10-26"));
        final UnidentifiableTransaction new1 = generator.randomUnidentifiableTransaction(LocalDate.parse("2019-08-25"));
        final UnidentifiableTransaction new2 = generator.randomUnidentifiableTransaction(LocalDate.parse("2019-07-04"));

        // when
        transactionService.save(UnidentifiableTransactions.of(Arrays.asList(new0, new1, new2)));

        // then
        Mockito.verify(transactionDao).insert(new0);
        Mockito.verify(transactionDao).insert(new1);
        Mockito.verify(transactionDao).insert(new2);
    }

    @Test
    public void save_overlap_insertsOnlyNew() {
        // given
        final UnidentifiableTransaction ut0 = generator.randomUnidentifiableTransaction(LocalDate.parse("2020-01-01"));
        final Transaction t0 = Transaction.create(UUID.randomUUID(), ut0);
        final Transaction t1 = generator.randomTransaction(LocalDate.parse("2020-02-01"));
        final Transaction t2 = generator.randomTransaction(LocalDate.parse("2020-03-01"));

        Mockito.when(transactionDao.find(Mockito.any(), Mockito.any())).thenReturn(Arrays.asList(t0, t1, t2));

        final UnidentifiableTransaction new1 = generator.randomUnidentifiableTransaction(LocalDate.parse("2019-08-25"));
        final UnidentifiableTransaction new2 = generator.randomUnidentifiableTransaction(LocalDate.parse("2019-07-04"));

        // when
        transactionService.save(UnidentifiableTransactions.of(Arrays.asList(ut0, new1, new2)));

        // then
        Mockito.verify(transactionDao, Mockito.never()).insert(ut0);
        Mockito.verify(transactionDao).insert(new1);
        Mockito.verify(transactionDao).insert(new2);
    }
}