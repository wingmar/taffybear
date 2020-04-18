package com.wingmar.taffybear.budget;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import java.util.UUID;

public class TransactionDaoMyBatis extends SqlSessionDaoSupport implements TransactionDao {

    @Override
    public Transaction insert(UnidentifiableTransaction unidentifiableTransaction) {
        final UUID id = getSqlSession().selectOne("transaction.newId");
        final Transaction transaction = Transaction.create(id, unidentifiableTransaction);
        getSqlSession().insert("transaction.insert", transaction);
        return transaction;
    }

    @Override
    public Transaction find(UUID id) {
        return getSqlSession().selectOne("transaction.find", id);
    }
}
