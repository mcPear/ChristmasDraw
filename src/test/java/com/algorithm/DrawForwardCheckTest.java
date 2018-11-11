package com.algorithm;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DrawForwardCheckTest {

    @Test
    public void testSolutions() {
        Result algorithmResult = new DrawForwardCheck(10, Options.getDefaultInstance()).run();
        Assert.assertTrue(algorithmResult.foundSolutions.size() <= 100);
        Assert.assertTrue(algorithmResult.foundSolutions.stream().noneMatch(this::hasSwap));
    }

    private boolean hasSwap(List<Integer> solution) {
        boolean result = false;
        for (int i = 0; i < solution.size() && !result; i += 2) {
            if (i + 2 == solution.get(i) && i + 1 == solution.get(i + 1)) {
                result = true;
            }
        }
        return result;
    }

}

//        0 1 2
//        1 2 1
//        2 3 4
//        3 4 3