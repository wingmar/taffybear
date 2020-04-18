package com.wingmar.taffybear.budget.tx;

import com.google.common.base.Preconditions;
import java.math.BigDecimal;
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
        return UnidentifiableTransaction.createUsdTransaction(
                randomMerchant(),
                randomTwentyFirstCenturyLocalDate(), randomFloatBigDecimal(500),
                randomString("category"),
                randomTransactionType());
    }

    public Merchant randomMerchant() {
        return Merchant.named(randomString("merchant"));
    }
}
