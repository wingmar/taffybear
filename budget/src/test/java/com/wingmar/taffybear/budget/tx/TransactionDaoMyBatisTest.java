package com.wingmar.taffybear.budget.tx;

import com.wingmar.taffybear.budget.BudgetApplicationContext;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BudgetApplicationContext.class)
@Transactional
public class TransactionDaoMyBatisTest extends TransactionDaoImplTest {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected TransactionDao getTransactionDaoUnderTest() {
        return transactionDao;
    }

    @Override
    protected String getUploadedFilenameByTransactionId(UUID transactionId) {
        return jdbcTemplate.queryForObject("SELECT filename FROM transaction_upload " +
                "WHERE transaction_id = " + "'" + transactionId + "'", String.class);
    }

    @Override
    protected void clearDataStore() {
        jdbcTemplate.update("DELETE FROM transaction_upload");
        jdbcTemplate.update("DELETE FROM transaction");
    }
}