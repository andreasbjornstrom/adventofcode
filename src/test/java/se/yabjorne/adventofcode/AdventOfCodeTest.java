package se.yabjorne.adventofcode;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AdventOfCodeTest {

    @Test
    public void testDay1SumAll() throws IOException {
        int[] integers = getTestDataAsInt("input.txt");

        assertEquals(590, AdventOfCode.day1_getTotalSum(integers));
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
        assertEquals(10, AdventOfCode.day1_ChronalCalibration(new int[]{3, 3, 4, -2, -4}));
        assertEquals(83445, AdventOfCode.day1_ChronalCalibration(getTestDataAsInt("input.txt")));
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

        assertEquals(12, AdventOfCode.day2InventoryManagementSystem(
                new String[]{"abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab"}));
        assertEquals(5880, AdventOfCode.day2InventoryManagementSystem(
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

        assertEquals("fgij", AdventOfCode.day2DifferByOneChar(
                new String[]{"abcde",
                        "fghij",
                        "klmno",
                        "pqrst",
                        "fguij",
                        "axcye",
                        "wvxyz"}));
        assertEquals("tiwcdpbseqhxryfmgkvjujvza", AdventOfCode.day2DifferByOneChar(
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

        assertEquals(4, AdventOfCode.day3FindOverlapClaimAreas(
                new String[]{
                        "#1 @ 1,3: 4x4",
                        "#2 @ 3,1: 4x4",
                        "#3 @ 5,5: 2x2"
                }
        ));
        assertEquals(100595, AdventOfCode.day3FindOverlapClaimAreas(
                getTestDataAsString("input_day3.txt")
        ));
    }
}
