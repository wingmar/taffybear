package com.wingmar.taffybear.budget.mybatis;

import com.wingmar.taffybear.budget.tx.Category;

public class CategoryTypeHandler extends NamedTypeHandler<Category> {
    @Override
    Category fromName(String name) {
        return Category.named(name);
    }
}
