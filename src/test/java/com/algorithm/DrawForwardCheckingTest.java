package com.algorithm;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

public class DrawForwardCheckingTest {

    @Test
    public void foundPermutationsHaveGivenSize() {
        final int size = 10;
        Result algorithmResult = new DrawForwardChecking(size, Options.getDefaults()).run();
        Assert.assertTrue(algorithmResult.foundSolutions.stream().allMatch(s -> s.size() == size));
    }

    @Test
    public void foundPermutationsHaveUniqueValues() {
        final int size = 10;
        Result algorithmResult = new DrawForwardChecking(size, Options.getDefaults()).run();
        Assert.assertTrue(algorithmResult.foundSolutions.stream().allMatch(this::hasUniqueValues));
    }

    private boolean hasUniqueValues(List<Integer> list) {
        return new HashSet<>(list).size() == list.size();
    }

    @Test
    public void shouldFindMaxOneHundredPermutationsForLargeSizeProblem() {
        final int size = 40;
        Result algorithmResult = new DrawForwardChecking(size, Options.getDefaults()).run();
        Assert.assertTrue(algorithmResult.foundSolutions.size() <= 100);
    }

    @Test
    public void foundPermutationsHaveNoSwaps() {
        final int size = 10;
        Result algorithmResult = new DrawForwardChecking(size, Options.getDefaults()).run();
        Assert.assertTrue(algorithmResult.foundSolutions.stream().noneMatch(this::hasSwap));
    }

    private boolean hasSwap(List<Integer> solution) {
        boolean hasSwap = false;
        for (int i = 0; i < solution.size() && !hasSwap; i += 2) {
            if (isSwap(i, solution)) {
                hasSwap = true;
            }
        }
        return hasSwap;
    }

    private boolean isSwap(int index, List<Integer> solution) {
        int currentValue = index + 1;
        int nextIndex = index + 1;
        int nextValue = nextIndex + 1;
        return nextValue == solution.get(index) && currentValue == solution.get(nextIndex);

    }

}