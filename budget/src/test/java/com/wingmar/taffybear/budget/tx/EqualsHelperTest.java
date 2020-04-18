package com.wingmar.taffybear.budget.tx;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class EqualsHelperTest {

    @Test
    public void elementPairsAreEqual_happyPath_onePair() {
        assertTrue(EqualsHelper.elementPairsAreEqual(1, 1));
    }

    @Test
    public void elementPairsAreEqual_sadPath_onePair() {
        assertFalse(EqualsHelper.elementPairsAreEqual(1, 0));
    }

    @Test
    public void elementPairsAreEqual_happyPath_multiplePairs() {
        assertTrue(EqualsHelper.elementPairsAreEqual(1, 1, "str", "str", false, false));
    }

    @Test
    public void elementPairsAreEqual_sadPath_multiplePairs() {
        assertFalse(EqualsHelper.elementPairsAreEqual(1, 1, "str", "str", false, true));
    }

    @Test
    public void elementPairsAreEqual_sadPath_multiplePairs_incorrectOrder() {
        assertFalse(EqualsHelper.elementPairsAreEqual(1, 1, "str", false, "str", true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void elementPairsAreEqual_oddInput() {
        EqualsHelper.elementPairsAreEqual(1, 1, 0);
    }
}