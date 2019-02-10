package se.yabjorne.adventofcode;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Day1 {


    public static int getTotalSum(int[] ints) throws IOException {

        return Arrays.stream(ints).sum();
    }


    public static int chronalCalibration(int[] integers) {
        int tot = 0;
        Set<Integer> historyOftotal = new HashSet<>();
        while (true) {
            for (int integer : integers) {
                tot += integer;
                if (historyOftotal.contains(tot))
                    return tot;
                historyOftotal.add(tot);
            }
        }
    }
}