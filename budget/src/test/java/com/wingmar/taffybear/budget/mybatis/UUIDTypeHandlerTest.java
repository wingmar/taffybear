package com.wingmar.taffybear.budget.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import java.util.UUID;
import java.util.function.Function;

public class UUIDTypeHandlerTest extends StringBasedTypeHandlerTest<UUID> {

    private final UUID uuid = UUID.randomUUID();

    @Override
    protected TypeHandler<UUID> getTypeHandler() {
        return new UUIDTypeHandler();
    }

    @Override
    protected Function<String, UUID> getCreator() {
        return UUID::fromString;
    }

    @Override
    protected String getTestColumnValue() {
        return uuid.toString();
    }

    @Override
    protected JdbcType getJdbcType() {
        return JdbcType.CHAR;
    }
}
