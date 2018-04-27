package com.algorithm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

//count all domains
public class QueenForwardCheck extends ForwardCheck {

    public QueenForwardCheck(int n, Options options) {
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

        //update columns at right
        int nextIndex = theLastValueIndex + 1;
        for (int i = theLastValueIndex + 1; i < updatedDomains.size(); i++) {
            List<Integer> domain = updatedDomains.get(i);
            domain.remove(theLastValue);
            domain.remove((Integer) (theLastValue + (i - nextIndex + 1)));
            domain.remove((Integer) (theLastValue - (i - nextIndex + 1)));
        }

        //update columns at left
        int previousIndex = theLastValueIndex - 1;
        for (int i = theLastValueIndex - 1; i >= 0; i--) {
            List<Integer> domain = updatedDomains.get(i);
            domain.remove(theLastValue);
            domain.remove((Integer) (theLastValue + (previousIndex - i + 1)));
            domain.remove((Integer) (theLastValue - (previousIndex - i + 1)));
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
    protected List<List<Integer>> getAllFullDomains() {
        List<List<Integer>> domains = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            domains.add(allKnownValues);
        }
        return domains;
    }


}
