package com.wingmar.taffybear.budget.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import java.util.function.Function;

public class NamedTypeHandlerTest extends StringBasedTypeHandlerTest<TestNamed> {

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
    protected Function<String, TestNamed> getCreator() {
        return TestNamed::new;
    }

    @Override
    protected String getTestColumnValue() {
        return "some value";
    }

    @Override
    protected JdbcType getJdbcType() {
        return JdbcType.VARCHAR;
    }
}
