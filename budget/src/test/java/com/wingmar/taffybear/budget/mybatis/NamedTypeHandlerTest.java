package com.wingmar.taffybear.budget.mybatis;

import com.wingmar.taffybear.budget.tx.Named;
import org.apache.ibatis.type.JdbcType;
import org.junit.Test;
import org.mockito.Mockito;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NamedTypeHandlerTest {

    private final NamedTypeHandler<TestNamed> handler = new NamedTypeHandler<TestNamed>() {
        @Override
        TestNamed fromName(String name) {
            return new TestNamed(name);
        }
    };

    @Test
    public void setParameter() throws SQLException {
        // given
        final PreparedStatement mock = Mockito.mock(PreparedStatement.class);
        final TestNamed named = new TestNamed("some name");

        // when
        handler.setParameter(mock, 0, named, JdbcType.VARCHAR);

        // then
        Mockito.verify(mock).setString(0, named.getName());
    }

    @Test
    public void getResult_columnName() throws SQLException {
        // given
        final ResultSet mock = Mockito.mock(ResultSet.class);
        Mockito.when(mock.getString("columnName")).thenReturn("returnString");

        // when
        final TestNamed actual = handler.getResult(mock, "columnName");

        // then
        assertThat(actual.getName(), is("returnString"));
    }

    @Test
    public void getResult_columnIndex() throws SQLException {
        // given
        final ResultSet mock = Mockito.mock(ResultSet.class);
        Mockito.when(mock.getString(0)).thenReturn("returnString");

        // when
        final TestNamed actual = handler.getResult(mock, 0);

        // then
        assertThat(actual.getName(), is("returnString"));
    }

    @Test
    public void getResult_callableStatement() throws SQLException {
        // given
        final CallableStatement mock = Mockito.mock(CallableStatement.class);
        Mockito.when(mock.getString(0)).thenReturn("returnString");

        // when
        final TestNamed actual = handler.getResult(mock, 0);

        // then
        assertThat(actual.getName(), is("returnString"));
    }

    class TestNamed implements Named {

        private final String name;

        TestNamed(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}