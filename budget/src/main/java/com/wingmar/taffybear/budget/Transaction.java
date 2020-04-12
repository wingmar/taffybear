package com.wingmar.taffybear.budget;

import com.google.common.base.MoreObjects;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private final Money amount;
    private final LocalDate date;
    private final String merchant;
    private final String category;
    private final TransactionType type;

    private Transaction(String merchant, LocalDate date, Money amount, String category, TransactionType type) {
        this.amount = amount;
        this.date = date;
        this.merchant = merchant;
        this.category = category;
        this.type = type;
    }

    public static Transaction createTransaction(String merchant, LocalDate date, Money amount, String category, TransactionType transactionType) {
        return new Transaction(merchant, date, amount, category, transactionType);
    }

    public static Transaction createUsdTransaction(String merchant, LocalDate date, BigDecimal amount, String category, TransactionType transactionType) {
        return createTransaction(merchant, date, Money.of(CurrencyUnit.USD, amount), category, transactionType);
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getCategory() {
        return category;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Transaction)) {
            return false;
        }

        final Transaction o = (Transaction) obj;
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
