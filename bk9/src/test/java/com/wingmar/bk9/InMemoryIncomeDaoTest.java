package com.wingmar.bk9;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class InMemoryIncomeDaoTest {

    private final InMemoryIncomeDao dao = InMemoryIncomeDao.fresh();

    @Test(expected = IllegalArgumentException.class)
    public void insert_existingIdThrowsIllegalArgumentException() {
        // given
        final Income income = Income.create(UUID.randomUUID(), LocalDate.now(), BigDecimal.ONE, "note", true);

        // when
        dao.insert(income);

        // then -> exception
    }

    @Test
    public void insert_stored() {
        // given
        final LocalDate now = LocalDate.now();
        final Income income = Income.unidentified(now, BigDecimal.ONE, "note", true);

        // when
        final UUID id = dao.insert(income);

        // then
        assertThat(dao.getMap(), is(ImmutableMap
                .of(id, Income.create(id, now, BigDecimal.ONE, "note", true))));
    }

    @Test
    public void find() {
        // given
        final LocalDate now = LocalDate.now();
        final Income income = Income.unidentified(now, BigDecimal.ONE, "note", true);

        final UUID id = dao.insert(income);

        // when
        final Optional<Income> actual = dao.find(id);

        // then
        assertThat(actual, is(Optional.of(Income.create(id, now, BigDecimal.ONE, "note", true))));
    }

    @Test
    public void find_notFoundReturnsEmpty() {
        // given

        // when
        final Optional<Income> actual = dao.find(UUID.randomUUID());

        // then
        assertThat(actual, is(Optional.empty()));
    }
}