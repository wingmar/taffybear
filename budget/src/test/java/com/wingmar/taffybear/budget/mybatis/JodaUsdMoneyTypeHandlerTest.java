package com.wingmar.taffybear.budget.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JodaUsdMoneyTypeHandlerTest extends AbstractTypeHandlerTest<Money> {

    private final BigDecimal getResultTestValue = BigDecimal.valueOf(54.32);

    @Override
    protected TypeHandler<Money> getTypeHandler() {
        return new JodaUsdMoneyTypeHandler();
    }

    private final double setParameterInternalValue = -128.77;

    @Override
    protected void setupMockedResultSet(ResultSet resultSet, String columnName, int columnIndex) throws
            SQLException {
        Mockito.when(resultSet.getBigDecimal(columnName)).thenReturn(getResultTestValue);
        Mockito.when(resultSet.getBigDecimal(columnIndex)).thenReturn(getResultTestValue);
    }

    @Override
    protected void setupMockedCallableStatement(CallableStatement callableStatement, int columnIndex) throws
            SQLException {
        Mockito.when(callableStatement.getBigDecimal(columnIndex)).thenReturn(getResultTestValue);
    }

    @Override
    protected Matcher<Money> getResultMatchesTestValue() {
        return Matchers.is(Money.of(CurrencyUnit.USD, getResultTestValue));
    }

    @Override
    protected Money getSetParameterEntity() {
        return Money.of(CurrencyUnit.USD, setParameterInternalValue);
    }

    @Override
    protected JdbcType getJdbcType() {
        return JdbcType.DECIMAL;
    }

    @Override
    protected void verifyPreparedStatementSetParameter(PreparedStatement preparedStatement) throws SQLException {
        Mockito.verify(preparedStatement).setBigDecimal(getColumnIndex(), BigDecimal.valueOf(setParameterInternalValue));
    }
}