package com.wingmar.taffybear.budget;

import com.mysql.jdbc.Driver;
import com.wingmar.taffybear.budget.tx.TransactionDao;
import com.wingmar.taffybear.budget.tx.TransactionDaoMyBatis;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import javax.sql.DataSource;

@Configuration
public class BudgetApplicationContext {

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl("jdbc:mysql://budgeter.czvbcrctc3sv.us-east-1.rds.amazonaws.com/budget");
        dataSource.setUsername("admin");
        dataSource.setPassword("taffybear");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("com/wingmar/taffybear/budget/config.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public TransactionManager transactionManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionDao transactionDao(@Autowired SqlSessionFactory sqlSessionFactory) throws Exception {
        final TransactionDaoMyBatis dao = new TransactionDaoMyBatis();
        dao.setSqlSessionFactory(sqlSessionFactory);
        return dao;
    }
}
