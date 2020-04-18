package com.wingmar.taffybear.budget.tx;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TestDataGeneratorTest {

    private TestDataGenerator generator;

    @Before
    public void before() {
        generator = TestDataGenerator.newInstance();
    }

    private void runTestRepeatedly(Runnable tester) {
        IntStream.range(0, 100000).forEach(i -> tester.run());
    }

    @Test
    public void randomIntBetween() {
        runTestRepeatedly(() -> {
            int min = 100;
            int max = 400000;
            // when
            final int randomInt = generator.randomIntBetween(min, max);

            // then
            final int amountGreaterThanMin = randomInt - min;
            assertThat(amountGreaterThanMin, greaterThanOrEqualTo(0));
            assertThat(amountGreaterThanMin, lessThanOrEqualTo(max - min));
        });
    }

    @Test
    public void randomString() {
        // given

        // when
        final String actual = generator.randomString("some string");

        // then
        assertThat(actual, Matchers.startsWith("some string"));
        assertThat(actual.length(), greaterThan("some string".length()));
    }

    @Test
    public void randomTwentyFirstCenturyLocalDate() {
        runTestRepeatedly(() -> {
            // when
            final LocalDate localDate = generator.randomTwentyFirstCenturyLocalDate();

            // then
            assertTrue(localDate.isAfter(LocalDate.of(1999, 12, 31)));
            assertTrue(localDate.isBefore(LocalDate.of(2100, 1, 1)));
        });
    }
}
