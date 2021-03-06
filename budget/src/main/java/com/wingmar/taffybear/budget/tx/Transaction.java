package com.wingmar.taffybear.budget.tx;

import com.google.common.base.MoreObjects;
import com.wingmar.taffybear.budget.Identifiable;
import org.joda.money.Money;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Transaction implements Identifiable {
    private final UUID id;
    private final UnidentifiableTransaction unidentifiableTransaction;

    private Transaction(UUID id, UnidentifiableTransaction unidentifiableTransaction) {
        this.id = id;
        this.unidentifiableTransaction = unidentifiableTransaction;
    }

    @SuppressWarnings("unused") // MyBatis
    public Transaction(UUID id, Merchant merchant, LocalDate date, BigDecimal amount, Category category,
                       TransactionType transactionType) {
        this(id, UnidentifiableTransaction.createUsdTransaction(merchant, date, amount, category, transactionType));
    }

    static Transaction create(UUID id, UnidentifiableTransaction unidentifiableTransaction) {
        return new Transaction(id, unidentifiableTransaction);
    }

    static Transaction create(Transaction transaction) {
        return new Transaction(transaction.getId(),
                UnidentifiableTransaction.createTransaction(transaction.getMerchant(),
                        transaction.getDate(),
                        transaction.getAmount(),
                        transaction.getCategory(),
                        transaction.getType()));
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UnidentifiableTransaction incognito() {
        return unidentifiableTransaction;
    }

    Money getAmount() {
        return unidentifiableTransaction.getAmount();
    }

    LocalDate getDate() {
        return unidentifiableTransaction.getDate();
    }

    Merchant getMerchant() {
        return unidentifiableTransaction.getMerchant();
    }

    Category getCategory() {
        return unidentifiableTransaction.getCategory();
    }

    TransactionType getType() {
        return unidentifiableTransaction.getType();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(id)
                .addValue(unidentifiableTransaction)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Transaction)) {
            return false;
        }

        final Transaction o = (Transaction) obj;
        return EqualsHelper.elementPairsAreEqual(getId(), o.getId(),
                unidentifiableTransaction, o.unidentifiableTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), unidentifiableTransaction);
    }
}
