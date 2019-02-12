package se.yabjorne.adventofcode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {
    private List<GuardShift> logs = new ArrayList<>();
    private Comparator<String> comparator = (s, t1) ->
            s.substring(0, 17).equals(t1.substring(0, 17))
                    ? s.compareTo(t1) * -1 : s.compareTo(t1);

    Day4(String[] logentries) {
        parse(logentries);
    }

    private void parse(String[] logentries) {
        //[1518-11-01 00:00] Guard #10 begins shift
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
        // Timestamps are written using year - month - day hour:
        // minute format.The guard falling asleep or waking up is always the one whose shift most recently started.
        // Because all asleep / awake times are during the midnight hour (00:00 - 00:59),only the minute portion (00 - 59)
        // is relevant for those events.
        GuardShift guardShift = null;
        List<String> sorted = Stream.of(logentries).sorted(comparator).collect(Collectors.toList());
        for (String logentry : sorted) {
            LocalDateTime date = LocalDateTime.parse(logentry.substring(1, 17), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            GuardAction action = GuardAction.of(logentry);
            if (action.equals(GuardAction.BEGINS_SHIFT)) {
                if (guardShift != null)
                    guardShift.endShift();
                int guardId = Integer.parseInt(logentry.split("#")[1].split(" ")[0]);
                guardShift = GuardShift.of(guardId, date);
                logs.add(guardShift);
            } else {
                guardShift.addEntry(date, action);
            }
        }

    }

    int findMinuteWhenGuardSleepsTheMost(int guardId) {
        Map<String, Integer> minutesWithCount = new HashMap<>();

        logs.stream().filter(gs -> gs.getGuardId() == guardId)
                .map(GuardShift::getSleepingMinues)
                .flatMap(List::stream)
                .forEach(anInt -> minutesWithCount.compute(anInt, (k, v) -> (v != null ? v : 0) + 1));

        int minuteWithMostSleep = -1;
        int amountOfSleep = -1;
        for (Map.Entry<String, Integer> minuteWithCount : minutesWithCount.entrySet()) {
            if (minuteWithCount.getValue() > amountOfSleep) {
                minuteWithMostSleep = Integer.parseInt(minuteWithCount.getKey());
                amountOfSleep = minuteWithCount.getValue();
            }
        }
        return minuteWithMostSleep;
    }

    int findGuardThatSleepsTheMost() {
        Map<Integer, List<GuardShift>> byGuardId = logs.stream().collect(Collectors.groupingBy(GuardShift::getGuardId));
        int guardId = -1;
        int maxMinOfSleep = -1;
        for (Map.Entry<Integer, List<GuardShift>> guardWithShifts : byGuardId.entrySet()) {
            int minuteOfSleep = guardWithShifts.getValue().stream().map(GuardShift::getSleepingMinues).mapToInt(List::size).sum();
            if (minuteOfSleep > maxMinOfSleep) {
                maxMinOfSleep = minuteOfSleep;
                guardId = guardWithShifts.getKey();
            }
        }
        return guardId;
    }

    int findGuardWhichSleepsTheMostForAnyMinute() {
        Map<Integer, List<GuardShift>> byGuardId = logs.stream().collect(Collectors.groupingBy(GuardShift::getGuardId));
        int guardId = -1;
        int maxMinOfSleep = -1;
        for (Map.Entry<Integer, List<GuardShift>> guardWithShifts : byGuardId.entrySet()) {
            String minuteWithMostOccurences = String.valueOf(findMinuteWhenGuardSleepsTheMost(guardWithShifts.getKey()));
            long minutesOfSleep = guardWithShifts.getValue()
                    .stream()
                    .map(GuardShift::getSleepingMinues)
                    .flatMap(List::stream)
                    .filter(m -> m.equals(minuteWithMostOccurences))
                    .count();
            if (minutesOfSleep > maxMinOfSleep) {
                maxMinOfSleep = (int) minutesOfSleep;
                guardId = guardWithShifts.getKey();
            }
        }

        return guardId;
    }

    private enum GuardAction {
        FALL_ASLEEP, WAKES_UP, BEGINS_SHIFT;

        public static GuardAction of(String logentry) {
            return logentry.contains("falls asleep") ? FALL_ASLEEP : logentry.contains("wakes up") ? WAKES_UP : BEGINS_SHIFT;
        }
    }

    private static class GuardShift {

        final List<String> sleepingMinues = new ArrayList<>();
        private final Integer guardId;
        private int lastActionMinute;
        private GuardAction lastAction;

        private GuardShift(Integer guardId, LocalDateTime date) {
            this.guardId = guardId;
            this.lastActionMinute = date.getHour() == 0 ?
                    date.getMinute() : 0;
        }

        static GuardShift of(Integer guardId, LocalDateTime date) {
            return new GuardShift(guardId, date);
        }

        Integer getGuardId() {
            return guardId;
        }

        List<String> getSleepingMinues() {
            return sleepingMinues;
        }

        void addEntry(LocalDateTime date, GuardAction action) {
            if (action.equals(GuardAction.WAKES_UP) && !lastAction.equals(GuardAction.FALL_ASLEEP))
                throw new IllegalStateException();
            if (date.getHour() != 0) {
                System.out.println("ignoring " + date + action);
                return;
            }
            if (action.equals(GuardAction.WAKES_UP))
                for (int minute = lastActionMinute; minute < date.getMinute(); minute++) {
                    sleepingMinues.add(String.valueOf(minute));
                }
            lastActionMinute = date.getMinute();
            lastAction = action;
        }

        void endShift() {
            if (GuardAction.FALL_ASLEEP.equals(lastAction))
                for (int minute = lastActionMinute; minute <= 59; minute++) {
                    sleepingMinues.add(String.valueOf(minute));
                }
        }
    }
}
