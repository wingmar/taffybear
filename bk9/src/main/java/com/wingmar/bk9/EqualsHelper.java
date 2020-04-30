package com.wingmar.bk9;

import com.google.common.base.Preconditions;
import java.util.Objects;
import java.util.Optional;

class EqualsHelper<T> {

    private final T thisOne;

    private EqualsHelper(T thisOne) {
        Preconditions.checkNotNull(thisOne);
        this.thisOne = thisOne;
    }

    static <T> EqualsHelper<T> of(T thisOne) {
        return new EqualsHelper<>(thisOne);
    }

    @SuppressWarnings("unchecked")
    Optional<T> cast(Object otherOne) {
        if (otherOne == null || thisOne.getClass() != otherOne.getClass()) {
            return Optional.empty();
        }

        return Optional.of((T) otherOne);
    }

    static boolean elementPairsAreEqual(Object... objects) {
        Preconditions.checkArgument(objects.length % 2 == 0);
        for (int i = 0; i < objects.length; i += 2) {
            final Object object = objects[i];
            final Object otherObject = objects[i + 1];
            if (!Objects.equals(object, otherObject)) {
                return false;
            }
        }

        return true;
    }
}

