package com.wingmar.taffybear.budget.tx;

import com.google.common.collect.ImmutableMap;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    public boolean logUpload(UUID transactionId, String filename) {
        return getSqlSession().insert("transaction.logUpload", ImmutableMap
                .of("transactionId", transactionId, "filename", filename)) == 1;
    }

    @Override
    public List<Transaction> all() {
        return getSqlSession().selectList("transaction.all");
    }

    @Override
    public Optional<Transaction> find(UUID id) {
        return Optional.ofNullable(getSqlSession().selectOne("transaction.find", id));
    }

    @Override
    public List<Transaction> find(LocalDate lower, LocalDate upper) {
        return getSqlSession().selectList("transaction.findByDateRange", ImmutableMap
                .of("lowerBound", lower, "upperBound", upper));
    }
}
