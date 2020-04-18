package com.wingmar.taffybear.budget.mybatis;

import com.wingmar.taffybear.budget.tx.Merchant;

public class MerchantTypeHandler extends NamedTypeHandler<Merchant> {
    @Override
    Merchant fromName(String name) {
        return Merchant.named(name);
    }
}
