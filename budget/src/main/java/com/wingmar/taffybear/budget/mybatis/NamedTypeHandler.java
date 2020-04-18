package com.wingmar.taffybear.budget.mybatis;

import com.wingmar.taffybear.budget.tx.Named;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class NamedTypeHandler<T extends Named> implements TypeHandler<T> {

    @Override
    public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getName());
    }

    @Override
    public T getResult(ResultSet rs, String columnName) throws SQLException {
        return fromName(rs.getString(columnName));
    }

    @Override
    public T getResult(ResultSet rs, int columnIndex) throws SQLException {
        return fromName(rs.getString(columnIndex));
    }

    @Override
    public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return fromName(cs.getString(columnIndex));
    }

    abstract T fromName(String name);
}
