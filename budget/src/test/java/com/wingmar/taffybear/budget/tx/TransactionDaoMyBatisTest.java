package com.wingmar.taffybear.budget.tx;

import com.wingmar.taffybear.budget.BudgetApplicationContext;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BudgetApplicationContext.class)
@Transactional
public class TransactionDaoMyBatisTest {

    private final TestDataGenerator generator = TestDataGenerator.newInstance();

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    public void insert_returnsTransaction() throws Exception {
        // given
        final UnidentifiableTransaction unidentifiableTransaction = generator.randomUnidentifiableTransaction();

        // when
        final Transaction transaction = transactionDao.insert(unidentifiableTransaction);

        // then
        assertThat(transaction, is(Transaction.create(transaction.getId(), unidentifiableTransaction)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void logUpload_referentialIntegrity() {
        // given

        // when
        transactionDao.logUpload(UUID.randomUUID(), "filename");

        // then
    }

    @Test
    public void logUpload() {
        // given
        final Transaction insert = transactionDao.insert(generator.randomUnidentifiableTransaction());

        // when
        final boolean logged = transactionDao.logUpload(insert.getId(), "filename");

        // then
        final String filename = jdbcTemplate.queryForObject("SELECT filename FROM transaction_upload " +
                "WHERE transaction_id = " + "'" + insert.getId() + "'", String.class);
        assertTrue(logged);
        assertThat(filename, is("filename"));
    }

    @Test
    public void all() {
        // given
        jdbcTemplate.update("delete from transaction");
        final int numInserted = 20;
        IntStream.range(0, numInserted).forEach(i -> transactionDao.insert(generator.randomUnidentifiableTransaction
                ()));

        // when
        final List<Transaction> all = transactionDao.all();

        assertThat(all.size(), is(numInserted));
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
    public void find_transactions_random() {
        // given
        final List<Transaction> transactions = generator.randomUnidentifiableTransactionList().stream()
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

    @Test
    public void find_transactions_exact_minInclusive() {
        // given
        final UnidentifiableTransaction ut0 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("tractor supply"),
                LocalDate.parse("2019-10-26"), BigDecimal.valueOf(101.43),
                Category.named("pet food"), TransactionType.SALE);

        final UnidentifiableTransaction ut1 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("mount airy inn"),
                LocalDate.parse("2019-12-17"), BigDecimal.valueOf(26.89),
                Category.named("restaurants"), TransactionType.SALE);

        final UnidentifiableTransaction ut2 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("safeway"),
                LocalDate.parse("2019-07-04"), BigDecimal.valueOf(56.78),
                Category.named("groceries"), TransactionType.SALE);

        final Transaction t0 = transactionDao.insert(ut0);
        final Transaction t1 = transactionDao.insert(ut1);
        final Transaction t2 = transactionDao.insert(ut2);

        // when
        final List<Transaction> actual = transactionDao.find(LocalDate.parse("2019-07-04"), LocalDate.parse
                ("2020-01-01"));

        // then
        assertThat(actual, Matchers.containsInAnyOrder(t0, t1, t2));
    }

    @Test
    public void find_transactions_exact_maxInclusive() {
        // given
        final UnidentifiableTransaction ut0 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("tractor supply"),
                LocalDate.parse("2019-10-26"), BigDecimal.valueOf(101.43),
                Category.named("pet food"), TransactionType.SALE);

        final UnidentifiableTransaction ut1 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("mount airy inn"),
                LocalDate.parse("2019-12-17"), BigDecimal.valueOf(26.89),
                Category.named("restaurants"), TransactionType.SALE);

        final UnidentifiableTransaction ut2 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("safeway"),
                LocalDate.parse("2019-07-04"), BigDecimal.valueOf(56.78),
                Category.named("groceries"), TransactionType.SALE);

        final Transaction t0 = transactionDao.insert(ut0);
        final Transaction t1 = transactionDao.insert(ut1);
        final Transaction t2 = transactionDao.insert(ut2);

        // when
        final List<Transaction> actual = transactionDao.find(LocalDate.parse("2019-07-04"), LocalDate.parse
                ("2019-12-17"));

        // then
        assertThat(actual, Matchers.containsInAnyOrder(t0, t1, t2));
    }

    @Test
    public void find_transactions_exact_minRestricts() {
        // given
        final UnidentifiableTransaction ut0 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("tractor supply"),
                LocalDate.parse("2019-10-26"), BigDecimal.valueOf(101.43),
                Category.named("pet food"), TransactionType.SALE);

        final UnidentifiableTransaction ut1 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("mount airy inn"),
                LocalDate.parse("2019-12-17"), BigDecimal.valueOf(26.89),
                Category.named("restaurants"), TransactionType.SALE);

        final UnidentifiableTransaction ut2 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("safeway"),
                LocalDate.parse("2019-07-04"), BigDecimal.valueOf(56.78),
                Category.named("groceries"), TransactionType.SALE);

        final Transaction t0 = transactionDao.insert(ut0);
        final Transaction t1 = transactionDao.insert(ut1);
        transactionDao.insert(ut2);

        // when
        final List<Transaction> actual = transactionDao.find(LocalDate.parse("2019-07-05"), LocalDate.parse
                ("2019-12-17"));

        // then
        assertThat(actual, Matchers.containsInAnyOrder(t0, t1));
    }

    @Test
    public void find_transactions_exact_maxRestricts() {
        // given
        final UnidentifiableTransaction ut0 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("tractor supply"),
                LocalDate.parse("2019-10-26"), BigDecimal.valueOf(101.43),
                Category.named("pet food"), TransactionType.SALE);

        final UnidentifiableTransaction ut1 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("mount airy inn"),
                LocalDate.parse("2019-12-17"), BigDecimal.valueOf(26.89),
                Category.named("restaurants"), TransactionType.SALE);

        final UnidentifiableTransaction ut2 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("safeway"),
                LocalDate.parse("2019-07-04"), BigDecimal.valueOf(56.78),
                Category.named("groceries"), TransactionType.SALE);

        final Transaction t0 = transactionDao.insert(ut0);
        transactionDao.insert(ut1);
        final Transaction t2 = transactionDao.insert(ut2);

        // when
        final List<Transaction> actual = transactionDao.find(LocalDate.parse("2019-07-04"), LocalDate.parse
                ("2019-12-16"));

        // then
        assertThat(actual, Matchers.containsInAnyOrder(t0, t2));
    }

    @Test
    public void find_transactions_exact_single() {
        // given
        final UnidentifiableTransaction ut0 = UnidentifiableTransaction.createUsdTransaction(Merchant
                        .named("tractor supply"),
                LocalDate.parse("2019-10-26"), BigDecimal.valueOf(101.43),
                Category.named("pet food"), TransactionType.SALE);

        final Transaction t0 = transactionDao.insert(ut0);

        // when
        final List<Transaction> actual = transactionDao.find(LocalDate.parse("2019-10-26"), LocalDate.parse
                ("2019-10-26"));

        // then
        assertThat(actual, Matchers.contains(t0));
    }
}