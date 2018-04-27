package com.algorithm;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class ForwardCheck extends CSPAlgorithm {

    public ForwardCheck(int n, Options options) {
        super(n, options);
    }

    private int getNextVariableDomainIndex(List<Integer> subSolution, List<List<Integer>> updatedDomains) {
        if (options.useMinimumDomainHeuristic) {
            int minimumDomainIndex = -1;
            List<Integer> minimumDomain = null;
            for (int i = 0; i < updatedDomains.size(); i++) {
                if (subSolution.get(i) == 0 && (minimumDomain == null || updatedDomains.get(i).size() < minimumDomain.size())) {
                    minimumDomainIndex = i;
                    minimumDomain = updatedDomains.get(i);
                }
            }
            return minimumDomainIndex;
        } else if (options.useMaximumDomainHeuristic) {
            int maximumDomainIndex = -1;
            List<Integer> maximumDomain = null;
            for (int i = 0; i < updatedDomains.size(); i++) {
                if (subSolution.get(i) == 0 && (maximumDomain == null || updatedDomains.get(i).size() > maximumDomain.size())) {
                    maximumDomainIndex = i;
                    maximumDomain = updatedDomains.get(i);
                }
            }
            return maximumDomainIndex;
        } else {
            return subSolution.indexOf(0);
        }
    }

    private void forwardCheck(List<Integer> subSolution, List<List<Integer>> domains, int index) {
        if (options.logProgress) {
            logProgress(subSolution);
        }
        if (options.countRecursiveCalls) recursiveCallsCount++;
        if (options.stopAtFirstSolution && !foundSolutions.isEmpty()) {
            return;
        }

        if (isFullSolution(subSolution)) {
            saveSolution(subSolution);
        } else {
            List<List<Integer>> updatedDomains = getUpdatedDomains(subSolution, domains, index);
            if (!containsEmptyDomainOnEmptyVariable(subSolution, updatedDomains)) {
                triggerForwardCheckForNextDomainValues(subSolution, updatedDomains);
            } else if (options.countReturns) {
                returnsCount++;
            }
        }

    }

    private void triggerForwardCheckForNextDomainValues(List<Integer> subSolution, List<List<Integer>> updatedDomains) {
        int nextVariableDomainIndex = getNextVariableDomainIndex(subSolution, updatedDomains);
        List<Integer> domainValues = updatedDomains.get(nextVariableDomainIndex);
        if (options.useRandomVariableValueHeuristic) {
            Collections.shuffle(domainValues);
        } else if (options.useMedianToEdgesValueHeuristic) {
            orderFromMedianToEdge(domainValues);
        } else if (options.useEdgesToMedianValueHeuristic) {
            orderFromMedianToEdge(domainValues);
            Collections.reverse(domainValues);
        }
        domainValues.forEach(domainValue -> {
            List<Integer> nextSolution = getNextSolution(subSolution, domainValue, nextVariableDomainIndex);
            forwardCheck(nextSolution, updatedDomains, nextVariableDomainIndex);
        });
    }

    protected abstract List<List<Integer>> getUpdatedDomains(List<Integer> subSolution, List<List<Integer>> domains, int index);

    private void sortDescendingFrequentDomainValues(List<Integer> values, List<Integer> subSolution) {
        Comparator descendingFrequencyComparator = Collections.reverseOrder(Comparator.comparing(val -> Collections.frequency(subSolution, val)));
        values.sort(descendingFrequencyComparator);
    }

    private void sortAscendingFrequentDomainValues(List<Integer> values, List<Integer> subSolution) {
        Comparator ascendingFrequencyComparator = Comparator.comparing(val -> Collections.frequency(subSolution, val));
        values.sort(ascendingFrequencyComparator);
    }

    public static void orderFromMedianToEdge(List<Integer> domain) {
        for (int i = 0; i < domain.size() - 1; i++) {
            Collections.swap(domain, i, getMedianIndexFrom(domain, i + 1));
        }
    }

    private static int getMedianIndexFrom(List<Integer> domain, int startIndex) {
        if (domain.size() - 1 == startIndex) {
            return startIndex;
        }
        List<Integer> sortedSubDomain = new ArrayList<>(domain.subList(startIndex, domain.size()));
        Collections.sort(sortedSubDomain);
        Integer medianFrom = sortedSubDomain.get((sortedSubDomain.size() - 1) / 2);
        return domain.indexOf(medianFrom);
    }

    protected List<Integer> getNextSolution(List<Integer> previousSolution, Integer nextValue, int index) {
        //int indexOfFirstZero = previousSolution.indexOf(0);
        List<Integer> nextSolution = new ArrayList<>(previousSolution);
        nextSolution.set(index, nextValue);
        return nextSolution;
    }

    public Result run() {
        long start = System.currentTimeMillis();
        forwardCheck(getInitialSolution(), getAllFullDomains(), -1);
        if (options.countExecutionTime) {
            executionTimeMillis = System.currentTimeMillis() - start;
        }
        return getResult();
    }

    protected List<List<Integer>> copyList2D(List<List<Integer>> list2D) {
        List<List<Integer>> result = new ArrayList<>();
        list2D.forEach(list -> result.add(new ArrayList<>(list)));
        return result;
    }

    private boolean containsEmptyDomainOnEmptyVariable(List<Integer> subSolution, List<List<Integer>> domains) {
        for (int i = 0; i < domains.size(); i++) {
            if (subSolution.get(i) == 0 && domains.get(i).size() == 0) {
                return true;
            }
        }
        return false;
    }

    protected abstract boolean equalsInitialSolution(List<Integer> solution);

    protected abstract List<List<Integer>> getAllFullDomains();

    protected boolean isFullSolution(List<Integer> solution) {
        return !solution.contains(0);
    }

}
