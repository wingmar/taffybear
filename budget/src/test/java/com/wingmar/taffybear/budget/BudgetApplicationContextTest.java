package com.wingmar.taffybear.budget;

import com.wingmar.taffybear.budget.tx.TransactionDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BudgetApplicationContext.class)
@Transactional
public class BudgetApplicationContextTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private TransactionManager transactionManager;
    @Autowired
    private TransactionDao transactionDao;

    @Test
    public void dataSource() throws Exception {
        assertThat(dataSource, not(nullValue()));
    }

    @Test
    public void sqlSessionFactory() throws Exception {
        assertThat(sqlSessionFactory, not(nullValue()));
    }

    @Test
    public void transactionManager() throws Exception {
        assertThat(transactionManager, not(nullValue()));
    }

    @Test
    public void transactionDao() throws Exception {
        assertThat(transactionDao, not(nullValue()));
    }
}
