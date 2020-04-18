package com.wingmar.taffybear.budget.tx;

import java.util.Arrays;

public enum TransactionType {
    SALE ("Sale"),
    RETURN ("Return"),
    PAYMENT ("Payment");

    private final String name;

    TransactionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TransactionType fromName(String name) {
        return Arrays.stream(values()).filter(type -> type.getName().equals(name)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
