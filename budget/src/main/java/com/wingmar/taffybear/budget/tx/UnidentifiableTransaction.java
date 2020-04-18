package com.wingmar.taffybear.budget.tx;

import com.google.common.base.MoreObjects;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class UnidentifiableTransaction {
    private final Money amount;
    private final LocalDate date;
    private final Merchant merchant;
    private final Category category;
    private final TransactionType type;

    private UnidentifiableTransaction(Merchant merchant, LocalDate date, Money amount, Category category,
                                      TransactionType type) {
        this.amount = amount;
        this.date = date;
        this.merchant = merchant;
        this.category = category;
        this.type = type;
    }

    static UnidentifiableTransaction createTransaction(Merchant merchant, LocalDate date, Money amount, Category
            category,
                                                       TransactionType transactionType) {
        return new UnidentifiableTransaction(merchant, date, amount, category, transactionType);
    }

    static UnidentifiableTransaction createUsdTransaction(Merchant merchant, LocalDate date, BigDecimal amount, Category category,
                                                          TransactionType transactionType) {
        return createTransaction(merchant, date, Money.of(CurrencyUnit.USD,
                amount.setScale(2, RoundingMode.HALF_UP)), category, transactionType);
    }

    Money getAmount() {
        return amount;
    }

    LocalDate getDate() {
        return date;
    }

    Merchant getMerchant() {
        return merchant;
    }

    Category getCategory() {
        return category;
    }

    TransactionType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof UnidentifiableTransaction)) {
            return false;
        }

        final UnidentifiableTransaction o = (UnidentifiableTransaction) obj;
        return Objects.equals(getAmount(), o.getAmount())
                && Objects.equals(getDate(), o.getDate())
                && Objects.equals(getMerchant(), o.getMerchant())
                && Objects.equals(getCategory(), o.getCategory())
                && Objects.equals(getType(), o.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAmount(), getDate(), getMerchant(), getCategory(), getType());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Merchant", getMerchant())
                .add("Date", getDate())
                .add("Amount", getAmount())
                .add("Category", getCategory())
                .add("TransactionType", getType())
                .toString();
    }
}
