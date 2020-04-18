package com.wingmar.taffybear.budget.mybatis;

import com.wingmar.taffybear.budget.tx.Named;

class TestNamed implements Named {

    private final String name;

    TestNamed(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
