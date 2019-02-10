package se.yabjorne.adventofcode;

import java.util.HashMap;

public class Day2 {
    public static int inventoryManagementSystem(String[] strings) {
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
    public static String differByOneChar(String[] strings) {
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

}
