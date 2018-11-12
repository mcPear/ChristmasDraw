package com.algorithm;

public class TempMain {

    private static Options options = new Options(false, true, true,
            false, true, true, false,
            false, false, false,
            false);

    public static void main(String... args) {
        Result result = new DrawForwardChecking(6, options).run();
        System.out.println(result);
        System.out.println(result.foundSolutions.get(60));
    }

}
