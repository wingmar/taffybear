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
    public Transaction(UUID id, String merchant, LocalDate date, BigDecimal amount, String category,
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

    private Money getAmount() {
        return unidentifiableTransaction.getAmount();
    }

    public LocalDate getDate() {
        return unidentifiableTransaction.getDate();
    }

    private String getMerchant() {
        return unidentifiableTransaction.getMerchant();
    }

    private String getCategory() {
        return unidentifiableTransaction.getCategory();
    }

    private TransactionType getType() {
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
        return Objects.equals(getId(), o.getId())
                && Objects.equals(unidentifiableTransaction, o.unidentifiableTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), unidentifiableTransaction);
    }
}
