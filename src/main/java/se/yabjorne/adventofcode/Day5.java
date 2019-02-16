package se.yabjorne.adventofcode;

import java.util.HashSet;
import java.util.Set;

public class Day5 {
    public static int polymerMaxReduction(String polymer) {
        Set<Character> chars = new HashSet<>();
        for (char current : polymer.toCharArray()) {
            chars.add(current);
            chars.remove(switchCase(current));
        }

        polymer = polymerReduction(polymer);
        int count = Integer.MAX_VALUE;
        for (Character current : chars) {
            int currentCount = polymerReduction(polymer.replaceAll("[" + current + switchCase(current) + "]+", "")).length();
            if (currentCount < count) {
                count = currentCount;
            }
        }

        return count;
    }

    private static char switchCase(Character current) {
        return Character.isLowerCase(current) ?
                Character.toUpperCase(current) :
                Character.toLowerCase(current);
    }

    public static String polymerReduction(String polymer) {

        StringBuilder builder = new StringBuilder(polymer);
        for (int i = 0; i < builder.length() - 1; ) {
            char current = builder.charAt(i);
            char next = builder.charAt(i + 1);
            char currentOtherCase = switchCase(current);

            if (currentOtherCase == next) {
                builder.deleteCharAt(i + 1);
                builder.deleteCharAt(i);
            } else {
                i++;
            }
        }
        if (polymer.length() > builder.length()) {
            return polymerReduction(builder.toString());
        } else {
            return builder.toString();
        }
    }
}
