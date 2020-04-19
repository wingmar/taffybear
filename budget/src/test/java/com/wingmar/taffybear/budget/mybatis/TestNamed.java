package com.wingmar.taffybear.budget.mybatis;

import com.google.common.base.MoreObjects;
import com.wingmar.taffybear.budget.tx.Named;
import java.util.Objects;

class TestNamed implements Named {

    private final String name;

    TestNamed(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !o.getClass().equals(this.getClass())) {
            return false;
        }

        final TestNamed other = (TestNamed) o;
        return Objects.equals(getName(), other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(getName())
                .toString();
    }
}
