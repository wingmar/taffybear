package com.wingmar.taffybear.budget;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import java.util.UUID;

public class TransactionDaoMyBatis extends SqlSessionDaoSupport implements TransactionDao {

    @Override
    public IdentifiableTransaction insert(Transaction transaction) {
        final UUID id = getSqlSession().selectOne("transaction.newId");
        final IdentifiableTransaction identifiableTransaction = IdentifiableTransaction.create(id, transaction);
        getSqlSession().insert("transaction.insert", identifiableTransaction);
        return identifiableTransaction;
    }

    @Override
    public IdentifiableTransaction find(UUID id) {
        return getSqlSession().selectOne("transaction.find", id);
    }
}
