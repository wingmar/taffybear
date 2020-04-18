package com.wingmar.taffybear.budget;

import com.google.common.base.MoreObjects;
import org.joda.money.Money;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class IdentifiableTransaction implements Identifiable {
    private final UUID id;
    private final Transaction transaction;

    private IdentifiableTransaction(UUID id, Transaction transaction) {
        this.id = id;
        this.transaction = transaction;
    }

    @SuppressWarnings("unused") // MyBatis
    public IdentifiableTransaction(UUID id, String merchant, LocalDate date, BigDecimal amount, String category,
                                   TransactionType transactionType) {
        this(id, Transaction.createUsdTransaction(merchant, date, amount, category, transactionType));
    }

    static IdentifiableTransaction create(UUID id, Transaction transaction) {
        return new IdentifiableTransaction(id, transaction);
    }

    static IdentifiableTransaction create(IdentifiableTransaction identifiableTransaction) {
        return new IdentifiableTransaction(identifiableTransaction.getId(),
                Transaction.createTransaction(identifiableTransaction.getMerchant(),
                        identifiableTransaction.getDate(),
                        identifiableTransaction.getAmount(),
                        identifiableTransaction.getCategory(),
                        identifiableTransaction.getType()));
    }

    @Override
    public UUID getId() {
        return id;
    }

    private Money getAmount() {
        return transaction.getAmount();
    }

    private LocalDate getDate() {
        return transaction.getDate();
    }

    private String getMerchant() {
        return transaction.getMerchant();
    }

    private String getCategory() {
        return transaction.getCategory();
    }

    private TransactionType getType() {
        return transaction.getType();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(id)
                .addValue(transaction)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IdentifiableTransaction)) {
            return false;
        }

        final IdentifiableTransaction o = (IdentifiableTransaction) obj;
        return Objects.equals(getId(), o.getId())
                && Objects.equals(transaction, o.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), transaction);
    }
}
