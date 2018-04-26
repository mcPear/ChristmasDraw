package com.maciek.algorithm;

import java.util.List;

public class Result {

    public final int n;
    public final List<List<Integer>> foundSolutions;
    public final Integer foundSolutionsCount;
    public final Integer recursiveCallsCount ;
    public final Integer returnsCount;
    public final Long executionTimeMillis;
    public final String algorithm;

    public Result(int n, List<List<Integer>> foundSolutions, Integer foundSolutionsCount, Integer recursiveCallsCount, Integer returnsCount, Long executionTimeMillis, String algorithm) {
        this.n = n;
        this.foundSolutions = foundSolutions;
        this.foundSolutionsCount = foundSolutionsCount;
        this.recursiveCallsCount = recursiveCallsCount;
        this.returnsCount = returnsCount;
        this.executionTimeMillis = executionTimeMillis;
        this.algorithm = algorithm;
    }

    @Override
    public String toString() {
        return "Result{" +
                "n= " + n +
                ", solutions= " + foundSolutionsCount +
                ", recursiveCalls= " + recursiveCallsCount +
                ", returns= " + returnsCount +
                ", time= " + executionTimeMillis +
                //", algorithm= " + algorithm +
                '}';
    }
}
