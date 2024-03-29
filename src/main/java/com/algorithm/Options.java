package com.algorithm;

public class Options {

    public final boolean logProgress;
    public final boolean countRecursiveCalls;
    public final boolean countReturns;
    public final boolean stopAtFirstSolution;
    public final boolean stopAtHundredSolutions;
    public final boolean countExecutionTime;
    public final boolean useMinimumDomainHeuristic;
    public final boolean useMaximumDomainHeuristic;
    public final boolean useRandomVariableValueHeuristic;
    public final boolean useMedianToEdgesValueHeuristic;
    public final boolean useEdgesToMedianValueHeuristic;

    public Options(boolean logProgress, boolean countRecursiveCalls, boolean countReturns, boolean stopAtFirstSolution,
                   boolean stopAtHundredSolutions, boolean countExecutionTime, boolean useMinimumDomainHeuristic,
                   boolean useMaximumDomainHeuristic, boolean useRandomVariableValueHeuristic,
                   boolean useMedianToEdgesValueHeuristic, boolean useEdgesToMedianValueHeuristic) {
        this.logProgress = logProgress;
        this.countRecursiveCalls = countRecursiveCalls;
        this.countReturns = countReturns;
        this.stopAtFirstSolution = stopAtFirstSolution;
        this.stopAtHundredSolutions = stopAtHundredSolutions;
        this.countExecutionTime = countExecutionTime;
        this.useMinimumDomainHeuristic = useMinimumDomainHeuristic;
        this.useMaximumDomainHeuristic = useMaximumDomainHeuristic;
        this.useRandomVariableValueHeuristic = useRandomVariableValueHeuristic;
        this.useMedianToEdgesValueHeuristic = useMedianToEdgesValueHeuristic;
        this.useEdgesToMedianValueHeuristic = useEdgesToMedianValueHeuristic;
    }

    public static Options getDefaults() {
        return new Options(false, false, false,
                false, true, false, false,
                false, false, false,
                false);
    }

}
