package com.wingmar.taffybear.budget.tx;

import com.google.common.base.MoreObjects;
import java.util.Objects;

public class Category implements Named {
    private final String name;

    private Category(String name) {
        this.name = name;
    }

    public static Category named(String name) {
        return new Category(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(getName())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o.getClass().equals(this.getClass()))) {
            return false;
        }

        final Category other = (Category) o;
        return EqualsHelper.elementPairsAreEqual(getName(), other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
