package com.wingmar.taffybear.budget.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertThat;

public abstract class AbstractTypeHandlerTest<TypeHandlerType> {

    private final String columnName = "columnName";
    private final int columnIndex = 0;
    private final ResultSet resultSet = Mockito.mock(ResultSet.class);
    private final CallableStatement callableStatement = Mockito.mock(CallableStatement.class);
    private final PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);

    protected abstract TypeHandler<TypeHandlerType> getTypeHandler();

    protected abstract void setupMockedResultSet(ResultSet resultSet, String columnName, int columnIndex)
            throws SQLException;

    protected abstract void setupMockedCallableStatement(CallableStatement callableStatement, int columnIndex) throws
            SQLException;

    protected abstract Matcher<TypeHandlerType> getResultMatchesTestValue();

    @Before
    public void before() throws SQLException {
        setupMockedResultSet(resultSet, columnName, columnIndex);
        setupMockedCallableStatement(callableStatement, columnIndex);
    }

    @Test
    public void getResult_columnName() throws SQLException {
        // given

        // when
        final TypeHandlerType actual = getTypeHandler().getResult(resultSet, columnName);

        // then
        assertThat(actual, getResultMatchesTestValue());
    }

    @Test
    public void getResult_columnIndex() throws SQLException {
        // given

        // when
        final TypeHandlerType actual = getTypeHandler().getResult(resultSet, columnIndex);

        // then
        assertThat(actual, getResultMatchesTestValue());
    }

    @Test
    public void getResult_callableStatement() throws SQLException {
        // given

        // when
        final TypeHandlerType actual = getTypeHandler().getResult(callableStatement, columnIndex);

        // then
        assertThat(actual, getResultMatchesTestValue());
    }

    protected abstract TypeHandlerType getSetParameterEntity();

    protected abstract JdbcType getJdbcType();

    protected abstract void verifyPreparedStatementSetParameter(PreparedStatement preparedStatement) throws
            SQLException;

    int getColumnIndex() {
        return columnIndex;
    }

    @Test
    public void setParameter() throws SQLException {
        // given

        // when
        getTypeHandler().setParameter(preparedStatement, columnIndex, getSetParameterEntity(), getJdbcType());

        // then
        verifyPreparedStatementSetParameter(preparedStatement);
    }
}
