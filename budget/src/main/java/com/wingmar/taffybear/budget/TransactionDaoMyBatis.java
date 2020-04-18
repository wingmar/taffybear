package com.wingmar.taffybear.budget;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import java.util.UUID;

public class TransactionDaoMyBatis extends SqlSessionDaoSupport implements TransactionDao {

    @Override
    public IdentifiableTransaction insert(UnidentifiableTransaction unidentifiableTransaction) {
        final UUID id = getSqlSession().selectOne("unidentifiableTransaction.newId");
        final IdentifiableTransaction identifiableTransaction = IdentifiableTransaction.create(id, unidentifiableTransaction);
        getSqlSession().insert("unidentifiableTransaction.insert", identifiableTransaction);
        return identifiableTransaction;
    }

    @Override
    public IdentifiableTransaction find(UUID id) {
        return getSqlSession().selectOne("transaction.find", id);
    }
}
