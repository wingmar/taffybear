package com.wingmar.taffybear.budget.tx;

import com.google.common.base.MoreObjects;
import java.util.Objects;

public class Merchant implements Named {

    private final String name;

    private Merchant(String name) {
        this.name = name;
    }

    public static Merchant named(String name) {
        return new Merchant(name);
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

        final Merchant other = (Merchant) o;
        return EqualsHelper.elementPairsAreEqual(getName(), other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
