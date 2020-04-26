package com.wingmar.taffybear.budget;

import com.wingmar.taffybear.budget.tx.InMemoryTransactionDao;
import com.wingmar.taffybear.budget.tx.TransactionDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BudgetApplicationContext.class)
public class TestBudgetApplicationContext {

    @Bean
    public TransactionDao transactionDao() {
        return InMemoryTransactionDao.newInstance();
    }
}
