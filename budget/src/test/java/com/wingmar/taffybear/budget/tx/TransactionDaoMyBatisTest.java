package com.wingmar.taffybear.budget.tx;

import com.wingmar.taffybear.budget.BudgetApplicationContext;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BudgetApplicationContext.class)
@Transactional
public class TransactionDaoMyBatisTest {

    private final TestDataGenerator generator = TestDataGenerator.newInstance();
    @Autowired
    private TransactionDao transactionDao;

    @Test
    public void insert_populatesId() throws Exception {
        // given
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();

        // when
        final Transaction transaction = transactionDao.insert(unidentifiableTransaction);

        // then
        assertThat(transaction.getId(), not(nullValue()));
    }

    @Test
    public void find() throws Exception {
        // given
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();
        final Transaction transaction = transactionDao.insert(unidentifiableTransaction);

        // when
        final Optional<Transaction> actual = transactionDao.find(transaction.getId());

        // then
        assertThat(actual, is(Optional.of(transaction)));
    }

    @Test
    public void find_notFoundReturnsEmpty() throws Exception {
        // given

        // when
        final Optional<Transaction> actual = transactionDao.find(UUID.randomUUID());

        // then
        assertThat(actual, is(Optional.empty()));
    }

    @Test
    public void find_transactions() {
        // given
        final List<Transaction> transactions = generator.randomUnidentifiableTransactionList(10).stream()
                .map(transactionDao::insert)
                .collect(Collectors.toList());
        final List<LocalDate> dates = transactions.stream().map(Transaction::getDate).collect(Collectors.toList());
        final LocalDate lowerBound = Collections.min(dates);
        final LocalDate upperBound = Collections.max(dates);

        // when
        final List<Transaction> actual = transactionDao.find(lowerBound, upperBound);

        // then
        assertThat(actual, Matchers.containsInAnyOrder(transactions.toArray(new Transaction[transactions.size()])));
    }
}