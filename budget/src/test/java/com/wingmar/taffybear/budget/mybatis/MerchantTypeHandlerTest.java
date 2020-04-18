package com.wingmar.taffybear.budget.mybatis;

import com.wingmar.taffybear.budget.tx.Merchant;
import com.wingmar.taffybear.budget.tx.TestDataGenerator;
import org.apache.ibatis.type.JdbcType;
import org.junit.Test;
import org.mockito.Mockito;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MerchantTypeHandlerTest {

    private final TestDataGenerator generator = TestDataGenerator.newInstance();
    private final MerchantTypeHandler handler = new MerchantTypeHandler();

    @Test
    public void setParameter() throws SQLException {
        // given
        final PreparedStatement mock = Mockito.mock(PreparedStatement.class);
        final Merchant merchant = generator.randomMerchant();

        // when
        handler.setParameter(mock, 0, merchant, JdbcType.VARCHAR);

        // then
        Mockito.verify(mock).setString(0, merchant.getName());
    }

    @Test
    public void getResult_columnName() throws SQLException {
        // given
        final ResultSet mock = Mockito.mock(ResultSet.class);
        Mockito.when(mock.getString("columnName")).thenReturn("returnString");

        // when
        final Merchant actual = handler.getResult(mock, "columnName");

        // then
        assertThat(actual, is(Merchant.named("returnString")));
    }

    @Test
    public void getResult_columnIndex() throws SQLException {
        // given
        final ResultSet mock = Mockito.mock(ResultSet.class);
        Mockito.when(mock.getString(0)).thenReturn("returnString");

        // when
        final Merchant actual = handler.getResult(mock, 0);

        // then
        assertThat(actual, is(Merchant.named("returnString")));
    }

    @Test
    public void getResult_callableStatement() throws SQLException {
        // given
        final CallableStatement mock = Mockito.mock(CallableStatement.class);
        Mockito.when(mock.getString(0)).thenReturn("returnString");

        // when
        final Merchant actual = handler.getResult(mock, 0);

        // then
        assertThat(actual, is(Merchant.named("returnString")));
    }
}