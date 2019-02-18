package se.yabjorne.adventofcode;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AdventOfCodeTest {

    @Test
    public void testDay1SumAll() throws IOException {
        int[] integers = getTestDataAsInt("input.txt");

        assertEquals(590, Day1.getTotalSum(integers));
    }

    private int[] getTestDataAsInt(String filename) throws IOException {
        String[] lines = getTestDataAsStrings(filename);
        return Arrays.stream(lines).mapToInt(Integer::parseInt).toArray();
    }

    @NotNull
    private String[] getTestDataAsStrings(String filename) throws IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(filename);
        assertNotNull(resource);
        File inputFile = new File(resource.getPath());
        return new String(Files.readAllBytes(inputFile.toPath())).split("\\n");
    }

    @NotNull
    private String getTestDataAsString(String filename) throws IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(filename);
        assertNotNull(resource);
        File inputFile = new File(resource.getPath());
        return new String(Files.readAllBytes(inputFile.toPath())).trim();
    }

    @Test
    public void testDay1ChronalCalibration() throws IOException {
        assertEquals(10, Day1.chronalCalibration(new int[]{3, 3, 4, -2, -4}));
        assertEquals(83445, Day1.chronalCalibration(getTestDataAsInt("input.txt")));
    }

    @Test
    public void testDay2InventoryManagementSystem() throws IOException {
        //abcdef contains no letters that appear exactly two or three times.
        //bababc contains two a and three b, so it counts for both.
        //abbcde contains two b, but no letter appears exactly three times.
        //abcccd contains three c, but no letter appears exactly two times.
        //aabcdd contains two a and two d, but it only counts once.
        //abcdee contains two e.
        //ababab contains three a and three b, but it only counts once.

        assertEquals(12, Day2.inventoryManagementSystem(
                new String[]{"abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab"}));
        assertEquals(5880, Day2.inventoryManagementSystem(
                getTestDataAsStrings("input_day2.txt")));
    }

    @Test
    public void testDay2DifferByOne() throws IOException {
        //abcdef contains no letters that appear exactly two or three times.
        //bababc contains two a and three b, so it counts for both.
        //abbcde contains two b, but no letter appears exactly three times.
        //abcccd contains three c, but no letter appears exactly two times.
        //aabcdd contains two a and two d, but it only counts once.
        //abcdee contains two e.
        //ababab contains three a and three b, but it only counts once.

        assertEquals("fgij", Day2.differByOneChar(
                new String[]{"abcde",
                        "fghij",
                        "klmno",
                        "pqrst",
                        "fguij",
                        "axcye",
                        "wvxyz"}));
        assertEquals("tiwcdpbseqhxryfmgkvjujvza", Day2.differByOneChar(
                getTestDataAsStrings("input_day2.txt")));
    }

    @Test
    public void testDay3Claims() throws IOException {
        // A claim like #123 @ 3,2: 5x4 means that claim ID 123 specifies a rectangle
        // 3 inches from the left edge,
        // 2 inches from the top edge,
        // 5 inches wide, and 4 inches tall.

        //...........
        //...........
        //...#####...
        //...#####...
        //...#####...
        //...#####...
        //...........
        //...........
        //...........

        assertEquals(4, Day3.findOverlapClaimAreas(
                new String[]{
                        "#1 @ 1,3: 4x4",
                        "#2 @ 3,1: 4x4",
                        "#3 @ 5,5: 2x2"
                }
        ));
        assertEquals(100595, Day3.findOverlapClaimAreas(
                getTestDataAsStrings("input_day3.txt")
        ));
    }

    @Test
    public void testDay3ClaimsFindNoOverlap() throws IOException {
        // A claim like #123 @ 3,2: 5x4 means that claim ID 123 specifies a rectangle
        // 3 inches from the left edge,
        // 2 inches from the top edge,
        // 5 inches wide, and 4 inches tall.

        //...........
        //...........
        //...#####...
        //...#####...
        //...#####...
        //...#####...
        //...........
        //...........
        //...........

        Integer[] result = new Integer[1];
        result[0] = 3;
        assertArrayEquals(result, Day3.findClaimWhichDoesntOverlap(
                new String[]{
                        "#1 @ 1,3: 4x4",
                        "#2 @ 3,1: 4x4",
                        "#3 @ 5,5: 2x2"
                }
        ));
        result[0] = 415;
        assertArrayEquals(result, Day3.findClaimWhichDoesntOverlap(
                getTestDataAsStrings("input_day3.txt")
        ));
    }

    @Test
    public void testDay4GuardDutyTimeslots() throws IOException {
        // [1518-11-01 00:00] Guard #10 begins shift
        //[1518-11-01 00:05] falls asleep
        //[1518-11-01 00:25] wakes up
        //[1518-11-01 00:30] falls asleep
        //[1518-11-01 00:55] wakes up
        //[1518-11-01 23:58] Guard #99 begins shift
        //[1518-11-02 00:40] falls asleep
        //[1518-11-02 00:50] wakes up
        //[1518-11-03 00:05] Guard #10 begins shift
        //[1518-11-03 00:24] falls asleep
        //[1518-11-03 00:29] wakes up
        //[1518-11-04 00:02] Guard #99 begins shift
        //[1518-11-04 00:36] falls asleep
        //[1518-11-04 00:46] wakes up
        //[1518-11-05 00:03] Guard #99 begins shift
        //[1518-11-05 00:45] falls asleep
        //[1518-11-05 00:55] wakes up
        Day4 day4 = new Day4(
                new String[]{
                        "[1518-11-01 00:00] Guard #10 begins shift",
                        "[1518-11-01 00:05] falls asleep",
                        "[1518-11-01 00:25] wakes up",
                        "[1518-11-01 00:30] falls asleep",
                        "[1518-11-01 00:55] wakes up",
                        "[1518-11-01 23:58] Guard #99 begins shift",
                        "[1518-11-02 00:40] falls asleep",
                        "[1518-11-02 00:50] wakes up",
                        "[1518-11-03 00:05] Guard #10 begins shift",
                        "[1518-11-03 00:24] falls asleep",
                        "[1518-11-03 00:29] wakes up",
                        "[1518-11-04 00:02] Guard #99 begins shift",
                        "[1518-11-04 00:36] falls asleep",
                        "[1518-11-04 00:46] wakes up",
                        "[1518-11-05 00:03] Guard #99 begins shift",
                        "[1518-11-05 00:45] falls asleep",
                        "[1518-11-05 00:55] wakes up"
                });
        assertEquals(10, day4.findGuardThatSleepsTheMost());
        assertEquals(24, day4.findMinuteWhenGuardSleepsTheMost(10));
        assertEquals(99, day4.findGuardWhichSleepsTheMostForAnyMinute());

        Day4 day4FullData = new Day4(getTestDataAsStrings("input_day4.txt"));
        assertEquals(1217, day4FullData.findGuardThatSleepsTheMost());
        assertEquals(40, day4FullData.findMinuteWhenGuardSleepsTheMost(1217));
        assertEquals(48680, 1217 * 40);

        int guardId = day4FullData.findGuardWhichSleepsTheMostForAnyMinute();
        assertEquals(34, day4FullData.findMinuteWhenGuardSleepsTheMost(guardId));
        assertEquals(94826, 34 * guardId);
    }

    @Test
    public void day5AlchemicalReduction2() throws IOException {
        // dabAcCaCBAcCcaDA  The first 'cC' is removed.
        //dabAaCBAcCcaDA    This creates 'Aa', which is removed.
        //dabCBAcCcaDA      Either 'cC' or 'Cc' are removed (the result is the same).
        //dabCBAcaDA        No further actions can be taken.
        String polymer = "dabAcCaCBAcCcaDA";

        assertEquals("dabC", Day5.polymerReduction("dabC"));
        assertEquals("", Day5.polymerReduction("aA"));
        assertEquals("Cxxxx", Day5.polymerReduction("aACxxxx"));
        assertEquals("C", Day5.polymerReduction("aAC"));
        assertEquals("C", Day5.polymerReduction("aAbBC"));
        assertEquals("xx", Day5.polymerReduction("aAcbBCxx"));
        assertEquals("J", Day5.polymerReduction("JoONn"));

        assertEquals("dabCBAcaDA", Day5.polymerReduction(polymer));
        //JVvoiICIicdDOmnNMNZzn
        assertEquals("J", Day5.polymerReduction(getTestDataAsString("input_day5.txt").substring(129, 150)));
        assertEquals(11364, Day5.polymerReduction(getTestDataAsString("input_day5.txt")).length());

        assertEquals(4, Day5.polymerMaxReduction("dabCBAcaDA"));
        assertEquals(4212, Day5.polymerMaxReduction(getTestDataAsString("input_day5.txt")));
    }

    @Test
    public void day6coordinateswtf() throws IOException {
        Day6 smallArea = Day6.of(new String[]{
                "1, 1",
                "1, 6",
                "8, 3",
                "3, 4",
                "5, 5",
                "8, 9"
        });
        Map<Character, Point> coordinates = smallArea.getCoordinates();
        assertEquals(6, coordinates.size());
        assertEquals(5, (int) coordinates.get('A').distance(coordinates.get('B')));
        assertTrue(smallArea.isInifinite(coordinates.get('A')));
        assertTrue(smallArea.isInifinite(coordinates.get('B')));
        assertTrue(smallArea.isInifinite(coordinates.get('C')));
        assertFalse(smallArea.isInifinite(coordinates.get('D')));
        assertFalse(smallArea.isInifinite(coordinates.get('E')));
        assertTrue(smallArea.isInifinite(coordinates.get('F')));

        assertEquals(9, smallArea.getNumberOfFieldsClosestTo(coordinates.get('D')));
        assertEquals(17, smallArea.getNumberOfFieldsClosestTo(coordinates.get('E')));
        assertEquals(17, smallArea.getLargestArea());

        Day6 largeArea = Day6.of(getTestDataAsStrings("input_day6.txt"));
        assertEquals(4215, largeArea.getLargestArea());

    }
}
