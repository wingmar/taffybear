package com.wingmar.bk9;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EqualsHelperTest {

    @Test
    public void elementPairsAreEqual_happy() {
        assertTrue(EqualsHelper.newInstance().elementPairsAreEqual(1, 1, "", "", 5L, 5L, "asdf", "asdf"));
    }

    @Test
    public void elementPairsAreEqual_wrongIndexes() {
        assertFalse(EqualsHelper.newInstance().elementPairsAreEqual(1, "", 1, ""));
    }
}
