package com.wingmar.taffybear.budget;

import com.google.common.base.MoreObjects;
import org.joda.money.Money;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class IdentifiableTransaction implements Identifiable {
    private final UUID id;
    private final UnidentifiableTransaction unidentifiableTransaction;

    private IdentifiableTransaction(UUID id, UnidentifiableTransaction unidentifiableTransaction) {
        this.id = id;
        this.unidentifiableTransaction = unidentifiableTransaction;
    }

    @SuppressWarnings("unused") // MyBatis
    public IdentifiableTransaction(UUID id, String merchant, LocalDate date, BigDecimal amount, String category,
                                   TransactionType transactionType) {
        this(id, UnidentifiableTransaction.createUsdTransaction(merchant, date, amount, category, transactionType));
    }

    static IdentifiableTransaction create(UUID id, UnidentifiableTransaction unidentifiableTransaction) {
        return new IdentifiableTransaction(id, unidentifiableTransaction);
    }

    static IdentifiableTransaction create(IdentifiableTransaction identifiableTransaction) {
        return new IdentifiableTransaction(identifiableTransaction.getId(),
                UnidentifiableTransaction.createTransaction(identifiableTransaction.getMerchant(),
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
        return unidentifiableTransaction.getAmount();
    }

    private LocalDate getDate() {
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
        if (obj == null || !(obj instanceof IdentifiableTransaction)) {
            return false;
        }

        final IdentifiableTransaction o = (IdentifiableTransaction) obj;
        return Objects.equals(getId(), o.getId())
                && Objects.equals(unidentifiableTransaction, o.unidentifiableTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), unidentifiableTransaction);
    }
}
