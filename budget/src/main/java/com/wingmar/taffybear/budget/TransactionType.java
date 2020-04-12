package com.wingmar.taffybear.budget;

import java.util.Arrays;

enum TransactionType {
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
