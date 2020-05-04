package com.wingmar.bk9;

public class InMemoryIncomeDao extends InMemoryGenericDao<Income> implements IncomeDao {
    private InMemoryIncomeDao() {
    }

    static InMemoryIncomeDao fresh() {
        return new InMemoryIncomeDao();
    }
}
