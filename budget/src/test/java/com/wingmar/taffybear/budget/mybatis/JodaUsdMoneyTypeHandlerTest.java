package com.wingmar.taffybear.budget.mybatis;

import org.apache.ibatis.type.TypeHandler;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JodaUsdMoneyTypeHandlerTest extends AbstractTypeHandlerTest<Money> {

    @Override
    protected TypeHandler<Money> getTypeHandler() {
        return new JodaUsdMoneyTypeHandler();
    }

    @Override
    protected void setupMockedResultSet(ResultSet resultSet, String columnName, int columnIndex) throws
            SQLException {
        Mockito.when(resultSet.getBigDecimal(columnName)).thenReturn(BigDecimal.valueOf(54.32));
        Mockito.when(resultSet.getBigDecimal(columnIndex)).thenReturn(BigDecimal.valueOf(54.32));
    }

    @Override
    protected void setupMockedCallableStatement(CallableStatement callableStatement, int columnIndex) throws
            SQLException {
        Mockito.when(callableStatement.getBigDecimal(columnIndex)).thenReturn(BigDecimal.valueOf(54.32));
    }

    @Override
    protected Matcher<Money> matchesTestValue() {
        return Matchers.is(Money.of(CurrencyUnit.USD, BigDecimal.valueOf(54.32)));
    }
}