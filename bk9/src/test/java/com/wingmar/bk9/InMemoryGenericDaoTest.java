package com.wingmar.bk9;

import com.google.common.collect.ImmutableMap;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertThat;

public class InMemoryGenericDaoTest {
    private final InMemoryGenericDao<TestImpl> dao = new InMemoryGenericDao<>();

    @Test(expected = IllegalArgumentException.class)
    public void insert_existingIdThrowsIllegalArgumentException() {
        // given
        final TestImpl test = new TestImpl(UUID.randomUUID());

        // when
        dao.insert(test);

        // then -> exception
    }

    @Test
    public void insert() {
        final TestImpl test = new TestImpl(null);

        // when
        final UUID insert = dao.insert(test);
        // given

        // then
        assertThat(dao.getState(), Matchers.is(ImmutableMap.of(insert, new TestImpl(insert))));
    }

    @Test
    public void find_notFoundReturnsEmpty() {
        // given

        // when
        final Optional<TestImpl> find = dao.find(UUID.randomUUID());

        // then
        assertThat(find, Matchers.is(Optional.empty()));
    }

    @Test
    public void find() {
        // given
        final TestImpl test = new TestImpl(null);
        final UUID insert = dao.insert(test);

        // when
        final Optional<TestImpl> find = dao.find(insert);

        // then
        assertThat(find, Matchers.is(Optional.of(new TestImpl(insert))));
    }

    private static class TestImpl implements Identifiable<TestImpl> {

        private final UUID id;

        TestImpl(UUID id) {
            this.id = id;
        }

        @Override
        public UUID getId() {
            return id;
        }

        @Override
        public TestImpl identify(UUID id) {
            return new TestImpl(id);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }

            final TestImpl other = (TestImpl) obj;
            return getId().equals(other.getId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId());
        }
    }
}