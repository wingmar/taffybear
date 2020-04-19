package com.wingmar.taffybear.budget.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.mockito.Mockito;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class StringBasedTypeHandlerTest<T> extends AbstractTypeHandlerTest<T> {

    protected abstract TypeHandler<T> getTypeHandler();

    @Override
    protected void setupMockedResultSet(ResultSet resultSet, String columnName, int columnIndex) throws SQLException {
        Mockito.when(resultSet.getString(columnName)).thenReturn(getInternalValue());
        Mockito.when(resultSet.getString(columnIndex)).thenReturn(getInternalValue());
    }

    @Override
    protected void setupMockedCallableStatement(CallableStatement callableStatement, int columnIndex) throws
            SQLException {
        Mockito.when(callableStatement.getString(columnIndex)).thenReturn(getInternalValue());
    }

    @Override
    protected Matcher<T> getResultMatchesTestValue() {
        return Matchers.is(getTestEntity());
    }

    @Override
    protected T getSetParameterEntity() {
        return getTestEntity();
    }

    protected abstract T getTestEntity();

    protected abstract String getInternalValue();

    protected abstract JdbcType getJdbcType();

    @Override
    protected void verifyPreparedStatementSetParameter(PreparedStatement preparedStatement) throws SQLException {
        Mockito.verify(preparedStatement).setString(getColumnIndex(), getInternalValue());
    }
}
