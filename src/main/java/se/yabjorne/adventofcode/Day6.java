package se.yabjorne.adventofcode;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Day6 {
    private static int maxX = -1;
    private static int maxY = -1;
    private final Map<Character, Point> coordinates;

    private Day6(String[] strings) {
        this.coordinates = createCoordinates(strings);
        populateField();
    }

    public static Map<Character, Point> createCoordinates(String[] coordinates) {
        Map<Character, Point> points = new HashMap<>();
        char a = 'A';
        for (String coordinate : coordinates) {
            String[] split = coordinate.split(",");
            int x = Integer.parseInt(split[0].trim());
            int y = Integer.parseInt(split[1].trim());
            maxX = x > maxX ? x : maxX;
            maxY = y > maxY ? y : maxY;

            points.put(a++, new Point(x, y));
        }
        return points;
    }

    public static Day6 of(String[] strings) {
        return new Day6(strings);
    }

    public boolean isInifinite(Point pointInFocus) {

        Character point = getCharacter(pointInFocus);

        char[][] fields = populateField();
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                if (x == fields[y].length - 1 || x == 0 || y == fields.length - 1 || y == 0)
                    if (fields[y][x] == point)
                        return true;
            }
        }
        return false;

    }

    @NotNull
    private Character getCharacter(Point pointInFocus) {
        return coordinates.entrySet()
                    .stream()
                    .filter(k -> k.getValue().equals(pointInFocus))
                    .map(Map.Entry::getKey)
                    .findAny().orElseThrow(IllegalStateException::new);
    }

    private int manhattanDistance(Point one, Point two) {
        return Math.abs(one.x - two.x) + Math.abs(one.y - two.y);
    }

    public Map<Character, Point> getCoordinates() {
        return coordinates;
    }

    public char[][] populateField() {
        char[][] field = new char[maxY + 1][maxX + 1];
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                char areaMarker = '?';
                int lowestDistance = Integer.MAX_VALUE;
                for (Map.Entry<Character, Point> characterPointEntry : coordinates.entrySet()) {
                    if (characterPointEntry.getValue().equals(new Point(x, y))) {
                        areaMarker = characterPointEntry.getKey();
                        break;
                    }
                    int distanceToPoint = manhattanDistance(characterPointEntry.getValue(), new Point(x, y));
                    if (distanceToPoint < lowestDistance) {
                        lowestDistance = distanceToPoint;
                        areaMarker = characterPointEntry.getKey();
                    } else if (distanceToPoint == lowestDistance) {
                        areaMarker = '.';
                    }
                }
                field[y][x] = areaMarker;
            }
        }
        return field;
    }

    public int getNumberOfFieldsClosestTo(Point d) {
        if (isInifinite(d))
            throw new IllegalStateException();
        int counter = 0;
        char[][] fields = populateField();
        for (char[] field : fields) {
            for (char c : field) {
                if (c == getCharacter(d))
                    counter++;
            }
        }
        System.out.println("Char: "+getCharacter(d)+" counter: "+counter);
        return counter;
    }

    public int getLargestArea() {
        Optional<Point> result = coordinates.values().parallelStream()
                .filter(p -> !isInifinite(p))
                .reduce((p1, p2) -> getNumberOfFieldsClosestTo(p1) > getNumberOfFieldsClosestTo(p2) ? p1 : p2);
        return getNumberOfFieldsClosestTo(result.orElseThrow(IllegalStateException::new));
    }

    public int getDistanceToallCoordinatesFromPoint(Point point) {
        return coordinates.values().stream().map(p -> manhattanDistance(p, point)).reduce(0, Integer::sum);
    }

    public int getSizeOfAreaWhereAllCoordinatesHasMaxDistance(int maxDistance) {
        char[][] field = populateField();
        int total = 0;
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[y].length; x++) {
                if (maxDistance >= getDistanceToallCoordinatesFromPoint(new Point(x, y)))
                    total++;
            }
        }
        return total;
    }
}
