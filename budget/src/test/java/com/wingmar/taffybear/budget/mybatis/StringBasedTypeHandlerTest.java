package com.wingmar.taffybear.budget.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public abstract class StringBasedTypeHandlerTest<T> extends AbstractTypeHandlerTest<T> {

    protected abstract TypeHandler<T> getTypeHandler();

    @Override
    protected void setupMockedResultSet(ResultSet resultSet, String columnName, int columnIndex) throws SQLException {
        Mockito.when(resultSet.getString(columnName)).thenReturn(getTestColumnValue());
        Mockito.when(resultSet.getString(columnIndex)).thenReturn(getTestColumnValue());
    }

    @Override
    protected void setupMockedCallableStatement(CallableStatement callableStatement, int columnIndex) throws
            SQLException {
        Mockito.when(callableStatement.getString(columnIndex)).thenReturn(getTestColumnValue());
    }

    @Override
    protected Matcher<T> matchesTestValue() {
        return Matchers.is(getCreator().apply(getTestColumnValue()));
    }

    protected abstract Function<String, T> getCreator();

    protected abstract Function<T, String> getStringGetter();

    protected abstract String getTestColumnValue();

    protected abstract JdbcType getJdbcType();

    @Test
    public void setParameter() throws SQLException {
        // given
        final PreparedStatement mock = Mockito.mock(PreparedStatement.class);
        final T someT = getCreator().apply(getTestColumnValue());

        // when
        getTypeHandler().setParameter(mock, 0, someT, getJdbcType());

        // then
        Mockito.verify(mock).setString(0, getStringGetter().apply(someT));
    }
}
