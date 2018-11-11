package com.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

//count all domains
public class DrawForwardCheck extends ForwardCheck {

    public DrawForwardCheck(int n, Options options) {
        super(n, options);
    }

    @Override
    protected List<List<Integer>> getUpdatedDomains(List<Integer> subSolution, List<List<Integer>> domains, int index) {
        if (equalsInitialSolution(subSolution)) {
            return domains;
        }

        int theLastValueIndex = index;
        Integer theLastValue = subSolution.get(theLastValueIndex);
        List<List<Integer>> updatedDomains = copyList2D(domains);

        //update cell
        updatedDomains.get(theLastValueIndex).clear();

        //update others
        for (int i = 0; i < updatedDomains.size(); i++) {
            updatedDomains.get(i).remove(theLastValue);
        }

        //prevent swaps
        if (index % 2 == 0 && theLastValue == (theLastValueIndex + 2)) {
            updatedDomains.get(theLastValueIndex + 1).remove(new Integer(theLastValueIndex + 1));
        }

        return updatedDomains;
    }

    @Override
    protected boolean equalsInitialSolution(List<Integer> solution) {
        HashSet<Integer> values = new HashSet<>(solution);
        return values.size() == 1 && values.contains(0);
    }

    @Override
    protected List<Integer> getInitialSolution() {
        return Collections.nCopies(n, 0);
    }

    @Override
    protected List<List<Integer>> getAllInitialDomains() {
        List<List<Integer>> domains = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            domains.add(new ArrayList<>(allKnownValues));
        }
        for (int i = 0; i < n; i++) {
            domains.get(i).remove(new Integer(i + 1));
        }
        return domains;
    }


}
