package com.wingmar.taffybear.budget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUsdTransaction();

        // when
        final IdentifiableTransaction identifiableTransaction = transactionDao.insert(unidentifiableTransaction);

        // then
        assertThat(identifiableTransaction.getId(), not(nullValue()));
    }

    @Test
    public void find() throws Exception {
        // given
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUsdTransaction();
        final IdentifiableTransaction identifiableTransaction = transactionDao.insert(unidentifiableTransaction);

        // when
        final IdentifiableTransaction actual = transactionDao.find(identifiableTransaction.getId());

        // then
        assertThat(actual, is(identifiableTransaction));
    }
}