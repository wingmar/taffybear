package com.wingmar.taffybear.budget.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.junit.Test;
import org.mockito.Mockito;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public abstract class StringBasedTypeHandlerTest<T> {

    protected abstract TypeHandler<T> getTypeHandler();

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

    @Test
    public void getResult_columnName() throws SQLException {
        // given
        final ResultSet mock = Mockito.mock(ResultSet.class);
        Mockito.when(mock.getString("columnName")).thenReturn(getTestColumnValue());

        // when
        final T actual = getTypeHandler().getResult(mock, "columnName");

        // then
        assertThat(getStringGetter().apply(actual), is(getTestColumnValue()));
    }

    @Test
    public void getResult_columnIndex() throws SQLException {
        // given
        final ResultSet mock = Mockito.mock(ResultSet.class);
        Mockito.when(mock.getString(0)).thenReturn(getTestColumnValue());

        // when
        final T actual = getTypeHandler().getResult(mock, 0);

        // then
        assertThat(getStringGetter().apply(actual), is(getTestColumnValue()));
    }

    @Test
    public void getResult_callableStatement() throws SQLException {
        // given
        final CallableStatement mock = Mockito.mock(CallableStatement.class);
        Mockito.when(mock.getString(0)).thenReturn(getTestColumnValue());

        // when
        final T actual = getTypeHandler().getResult(mock, 0);

        // then
        assertThat(getStringGetter().apply(actual), is(getTestColumnValue()));
    }
}
