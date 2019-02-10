package se.yabjorne.adventofcode;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AdventOfCode {


    public static int day1_getTotalSum(int[] ints) throws IOException {

        return Arrays.stream(ints).sum();
    }


    public static int day1_ChronalCalibration(int[] integers) {
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

    public static int day2InventoryManagementSystem(String[] strings) {
        int foundThree = 0;
        int foundTwo = 0;
        for (String string : strings) {
            HashMap<Character, Integer> hits = new HashMap<>();
            char[] chars = string.toCharArray();
            for (char aChar : chars) {
                hits.merge(aChar, 1, (p, n) -> p + n);
            }
            if (hits.containsValue(2))
                foundTwo += 1;
            if (hits.containsValue(3))
                foundThree += 1;
        }
        return foundTwo * foundThree;
    }

    public static String day2DifferByOneChar(String[] strings) {
        for (String string1 : strings) {
            for (String string2 : strings) {
                if (string1.equals(string2))
                    continue;
                int misses = 0;
                for (int i = 0; i < string1.length(); i++) {
                    if (string1.charAt(i) != string2.charAt(i))
                        misses += 1;
                }
                if (misses < 2) {
                    StringBuilder newString = new StringBuilder();
                    for (int i = 0; i < string1.length(); i++) {
                        if (string1.charAt(i) == string2.charAt(i))
                            newString.append(string1.charAt(i));
                    }
                    return newString.toString();
                }
            }
        }
        throw new IllegalStateException("unable to find matches..");
    }

    public static int day3FindOverlapClaimAreas(String[] strings) {
        //#1 @ 1,3: 4x4
        //#2 @ 3,1: 4x4
        //#3 @ 5,5: 2x2
        int maxWidth = 0;
        int maxHeight = 0;
        for (String string : strings) {
            String claimName = string.split(" ")[0];
            int startPointFromLeftEdge = getStartPointFromLeftEdge(string);
            int startPointFromTop = getStartPointFromTop(string);
            int squareSizeLeft = getSquareSizeLeft(string);
            int squareSizeDown = getSquareSizeDown(string);
            maxWidth = startPointFromLeftEdge + squareSizeLeft;
            maxHeight = startPointFromTop + squareSizeDown;
        }

        int[][] area = new int[maxWidth][maxHeight];

        for (String string : strings) {
            for (int i = getStartPointFromLeftEdge(string) -1; i < getSquareSizeLeft(string); i++) {
                area[getStartPointFromTop(string)][i] += 1;
            }
            System.out.println(string);
            getCoveredArea(area);
            for (int i = getStartPointFromTop(string); i < getSquareSizeDown(string); i++) {
                area[i][getSquareSizeDown(string)] += 1;
            }
            System.out.println(string);
            getCoveredArea(area);

        }

        return getCoveredArea(area);
    }

    private static int getCoveredArea(int[][] area) {
        int coveredArea = 0;
        for (int[] ints : area) {
            System.out.print("/");
            for (int anInt : ints) {
                if (anInt > 1) {
                    coveredArea += 1;
                    System.out.print("X");
                } else {
                    System.out.print(anInt);
                }
            }
            System.out.println("");
        }
        return coveredArea;
    }

    private static int getStartPointFromLeftEdge(String string) {
        return Integer.parseInt(string.split(" ")[2].split(",")[0]);
    }

    private static int getStartPointFromTop(String string) {
        return Integer.parseInt(string.split(" ")[2].split(",")[1].split(":")[0]);
    }

    private static int getSquareSizeLeft(String string) {
        return Integer.parseInt(string.split(" ")[3].split("x")[0]);
    }

    private static int getSquareSizeDown(String string) {
        return Integer.parseInt(string.split(" ")[3].split("x")[1]);
    }
}