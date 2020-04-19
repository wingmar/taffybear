package com.wingmar.taffybear.budget.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class NamedTypeHandlerTest extends StringBasedTypeHandlerTest<TestNamed> {

    private final String someTestString = "someTestString";

    @Override
    protected TypeHandler<TestNamed> getTypeHandler() {
        return new NamedTypeHandler<TestNamed>() {
            @Override
            TestNamed fromName(String name) {
                return new TestNamed(name);
            }
        };
    }

    @Override
    protected TestNamed getTestEntity() {
        return new TestNamed(someTestString);
    }

    @Override
    protected String getInternalValue() {
        return someTestString;
    }

    @Override
    protected JdbcType getJdbcType() {
        return JdbcType.VARCHAR;
    }
}
