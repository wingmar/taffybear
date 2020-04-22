package com.wingmar.taffybear.budget.tx;

import org.junit.Before;
import java.util.Map;
import java.util.UUID;

public class InMemoryTransactionDaoTest extends TransactionDaoImplTest {

    private InMemoryTransactionDao transactionDao;

    @Before
    public void before() {
        transactionDao = InMemoryTransactionDao.newInstance();
    }

    @Override
    protected TransactionDao getTransactionDaoUnderTest() {
        return transactionDao;
    }

    @Override
    protected String getUploadedFilenameByTransactionId(UUID transactionId) {
        return transactionDao.getUploads().asMap().entrySet().stream()
                .filter(e -> e.getValue().contains(transactionId))
                .map(Map.Entry::getKey).findFirst().orElse(null);
    }

    @Override
    protected void clearDataStore() {
        // new instance created in before
    }
}