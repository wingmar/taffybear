package com.wingmar.taffybear.budget.tx;

import com.google.common.base.Preconditions;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestDataGenerator {

    private final Random random;

    private TestDataGenerator(Random random) {
        this.random = random;
    }

    private TestDataGenerator() {
        this(new Random());
    }

    public static TestDataGenerator newInstance() {
        return new TestDataGenerator();
    }

    private int randomInt() {
        return random.nextInt(Integer.MAX_VALUE);
    }

    private double randomFloat(float max) {
        return random.nextFloat() * max;
    }

    int randomIntBetween(int min, int max) {
        Preconditions.checkArgument(min < max);
        return random.ints(min, max + 1)
                .findFirst().getAsInt();
    }

    private long randomLongBetween(long min, long max) {
        Preconditions.checkArgument(min < max);
        return random.longs(min, max + 1)
                .findFirst().getAsLong();
    }

    private BigDecimal randomFloatBigDecimal(float max) {
        return BigDecimal.valueOf(randomFloat(max));
    }

    String randomString(String prefix) {
        return prefix + randomInt();
    }

    LocalDate randomTwentyFirstCenturyLocalDate() {
        final Instant min = LocalDate.of(2000, 1, 1).atStartOfDay().toInstant(ZoneOffset.UTC);
        final Instant max = LocalDate.of(2099, 12, 31).atStartOfDay().toInstant(ZoneOffset.UTC);
        final Instant instant = Instant.ofEpochSecond(randomLongBetween(min.getEpochSecond(), max.getEpochSecond()));
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC")).toLocalDate();
    }

    private <T> Optional<T> randomElementOf(T[] array) {
        return randomElementOf(Arrays.asList(array));
    }

    private <T> Optional<T> randomElementOf(List<T> collection) {
        Preconditions.checkNotNull(collection);
        if (collection.isEmpty()) {
            return Optional.empty();
        }

        Collections.shuffle(collection);
        return Optional.ofNullable(collection.get(0));
    }

    private TransactionType randomTransactionType() {
        return randomElementOf(TransactionType.values()).orElseThrow(IllegalStateException::new);
    }

    Transaction randomTransaction() {
        return Transaction.create(UUID.randomUUID(), randomUnidentifiableTransaction());
    }

    List<UnidentifiableTransaction> randomUnidentifiableTransactionList(int size) {
        return IntStream.range(0, size).mapToObj(i -> randomUnidentifiableTransaction()).collect(Collectors.toList());
    }

    UnidentifiableTransaction randomUnidentifiableTransaction() {
        return UnidentifiableTransaction.createTransaction(randomMerchant(), randomTwentyFirstCenturyLocalDate(),
                Money.of(CurrencyUnit.USD,
                randomFloatBigDecimal(500).setScale(2, RoundingMode.HALF_UP)), Category.named(randomString
                        ("category")), randomTransactionType());
    }

    public Merchant randomMerchant() {
        return randomNamed(Merchant::named);
    }

    public Category randomCategory() {
        return randomNamed(Category::named);
    }

    public <T extends Named> T randomNamed(Function<String, T> namedCreator) {
        return namedCreator.apply(randomString("someName"));
    }
}
