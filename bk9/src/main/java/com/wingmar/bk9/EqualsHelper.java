package com.wingmar.bk9;

import com.google.common.base.Preconditions;
import java.util.Objects;

class EqualsHelper {

    private EqualsHelper() {
        // static access only
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

