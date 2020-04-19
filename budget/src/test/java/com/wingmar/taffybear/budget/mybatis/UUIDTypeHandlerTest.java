package com.wingmar.taffybear.budget.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import java.util.UUID;

public class UUIDTypeHandlerTest extends StringBasedTypeHandlerTest<UUID> {

    private final UUID uuid = UUID.randomUUID();

    @Override
    protected TypeHandler<UUID> getTypeHandler() {
        return new UUIDTypeHandler();
    }

    @Override
    protected UUID getTestEntity() {
        return uuid;
    }

    @Override
    protected String getInternalValue() {
        return uuid.toString();
    }

    @Override
    protected JdbcType getJdbcType() {
        return JdbcType.CHAR;
    }
}
