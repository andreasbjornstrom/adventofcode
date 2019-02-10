package se.yabjorne.adventofcode;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AdventOfCodeTest {

    @Test
    public void testDay1SumAll() throws IOException {
        int[] integers = getTestDataAsInt("input.txt");

        assertEquals(590, Day1.getTotalSum(integers));
    }

    private int[] getTestDataAsInt(String filename) throws IOException {
        String[] lines = getTestDataAsString(filename);
        return Arrays.stream(lines).mapToInt(Integer::parseInt).toArray();
    }

    @NotNull
    private String[] getTestDataAsString(String filename) throws IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(filename);
        assertNotNull(resource);
        File inputFile = new File(resource.getPath());
        return new String(Files.readAllBytes(inputFile.toPath())).split("\\n");
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
                getTestDataAsString("input_day2.txt")));
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
                getTestDataAsString("input_day2.txt")));
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
                getTestDataAsString("input_day3.txt")
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
                getTestDataAsString("input_day3.txt")
        ));
    }

}
