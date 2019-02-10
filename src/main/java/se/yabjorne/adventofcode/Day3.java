package se.yabjorne.adventofcode;

import java.util.HashSet;
import java.util.Set;

public class Day3 {
    public static int findOverlapClaimAreas(String[] claims) {
        int[][] field = addClaimsToField(claims);
        return getOverlappingArea(field, false);
    }

    private static int[][] addClaimsToField(String[] claims) {
        int[][] field = getMinSizeOfFieldNeeded(claims);
        System.out.printf("Area: %dx%d\n", field.length, field[0].length);
        for (String claim : claims) {
            System.out.println(claim);
            for (int x = getStartPointFromLeftEdge(claim); x < getStartPointFromLeftEdge(claim) + getSquareSizeLeft(claim); x++) {
                for (int y = getStartPointFromTop(claim); y < getStartPointFromTop(claim) + getSquareSizeDown(claim); y++) {
                    field[y][x] += 1;
                }
            }
        }
        return field;
    }

    private static int[][] getMinSizeOfFieldNeeded(String[] claims) {
        int maxWidth = 0;
        int maxHeight = 0;
        for (String claim : claims) {
            int newMaxWidth = getStartPointFromLeftEdge(claim) + getSquareSizeLeft(claim);
            maxWidth = newMaxWidth > maxWidth ? newMaxWidth : maxWidth;
            int newMaxHeight = getStartPointFromTop(claim) + getSquareSizeDown(claim);
            maxHeight = newMaxHeight > maxHeight ? newMaxHeight : maxHeight;
        }
        return new int[maxWidth + 1][maxHeight + 1];
    }

    private static int getOverlappingArea(int[][] area, boolean debug) {
        int coveredArea = 0;
        for (int[] ints : area) {
            for (int anInt : ints) {
                if (anInt > 1) {
                    coveredArea += 1;
                    if (debug) System.out.print("X");
                } else {
                    if (debug) System.out.print(anInt);
                }
            }
            if (debug) System.out.println("");
        }
        return coveredArea;
    }

    private static int getStartPointFromLeftEdge(String claim) {
        return Integer.parseInt(claim.split(" ")[2].split(",")[0]);
    }

    private static int getStartPointFromTop(String claim) {
        return Integer.parseInt(claim.split(" ")[2].split(",")[1].split(":")[0]);
    }

    private static int getSquareSizeLeft(String claim) {
        return Integer.parseInt(claim.split(" ")[3].split("x")[0]);
    }

    private static int getSquareSizeDown(String claim) {
        return Integer.parseInt(claim.split(" ")[3].split("x")[1]);
    }

    public static Integer[] findClaimWhichDoesntOverlap(String[] claims) {
        Set<Integer> claimsWithOverlap = new HashSet<>();
        Set<Integer> claimNames = new HashSet<>();
        int[][] fieldWithOverlappingClaims = addClaimsToField(claims);
        for (String claim : claims) {
            Integer claimName = Integer.valueOf(claim.split(" ")[0].substring(1));
            claimNames.add(claimName);
            System.out.println(claim);
            for (int x = getStartPointFromLeftEdge(claim); x < getStartPointFromLeftEdge(claim) + getSquareSizeLeft(claim); x++) {
                for (int y = getStartPointFromTop(claim); y < getStartPointFromTop(claim) + getSquareSizeDown(claim); y++) {
                    if (fieldWithOverlappingClaims[y][x] != 1){
                        claimsWithOverlap.add(claimName);
                    }
                }
            }
        }
        claimNames.removeAll(claimsWithOverlap);
        return claimNames.toArray(new Integer[0]);
    }
}
