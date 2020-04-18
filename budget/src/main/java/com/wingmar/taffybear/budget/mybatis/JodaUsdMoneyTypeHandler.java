package com.wingmar.taffybear.budget.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JodaUsdMoneyTypeHandler implements TypeHandler<Money> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Money parameter, JdbcType jdbcType) throws SQLException {
        ps.setBigDecimal(i, parameter.getAmount());
    }

    private Money usdScaled(BigDecimal bigDecimal) throws SQLException {
        return Money.of(CurrencyUnit.USD, bigDecimal.setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public Money getResult(ResultSet rs, String columnName) throws SQLException {
        return usdScaled(rs.getBigDecimal(columnName));
    }

    @Override
    public Money getResult(ResultSet rs, int columnIndex) throws SQLException {
        return usdScaled(rs.getBigDecimal(columnIndex));
    }

    @Override
    public Money getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return usdScaled(cs.getBigDecimal(columnIndex));
    }
}
