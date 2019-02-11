package se.yabjorne.adventofcode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day4 {
    private List<GuardShift> logs = new ArrayList<>();

    public Day4(String[] logentries) {
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
        for (String logentry : logentries) {
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

    public int findMinutWhenGuardSleepsTheMost(int guardId) {
        Map<Integer, Integer> minuteWithCount = new HashMap<>();

        logs.stream().filter(gs -> gs.getGuardId() == guardId)
                .map(GuardShift::getSleepingMinues)
                .flatMap(List::stream)
                .forEach(anInt -> minuteWithCount.compute(anInt, (k, v) -> (v != null ? v : 0) + 1));

        int minuteWithMostSleep = -1;
        int amountOfSleep = -1;
        for (Integer minute = 0; minute < 59; minute++) {
            if (minuteWithCount.getOrDefault(minute, 0) > amountOfSleep) {
                minuteWithMostSleep = minute;
                amountOfSleep = minuteWithCount.getOrDefault(minute, 0);
            }
        }
        return minuteWithMostSleep;
    }

    public int findGuardThatSleepsTheLeast() {
        Map<List<Integer>, List<GuardShift>> bySleeping = logs.stream().collect(Collectors.groupingBy(GuardShift::getSleepingMinues));
        int guardId = 0;
        int currentMaxSleeper = 0;
        for (Map.Entry<List<Integer>, List<GuardShift>> listListEntry : bySleeping.entrySet()) {
            if (listListEntry.getKey().size() > currentMaxSleeper) {
                currentMaxSleeper = listListEntry.getKey().size();
                guardId = listListEntry.getValue().get(0).getGuardId();
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

        final List<Integer> sleepingMinues = new ArrayList<>();
        private final Integer guardId;
        private int lastActionMinute;
        private GuardAction lastAction;

        private GuardShift(Integer guardId, LocalDateTime date) {
            this.guardId = guardId;
            this.lastActionMinute = date.getHour() == 0 ?
                    date.getMinute() : 0;
        }

        public static GuardShift of(Integer guardId, LocalDateTime date) {
            return new GuardShift(guardId, date);
        }

        public Integer getGuardId() {
            return guardId;
        }

        public List<Integer> getSleepingMinues() {
            return sleepingMinues;
        }

        public void addEntry(LocalDateTime date, GuardAction action) {
            if (date.getHour() != 0)
                return;
            if (action.equals(GuardAction.WAKES_UP))
                for (int minute = lastActionMinute; minute < date.getMinute(); minute++) {
                    sleepingMinues.add(minute);
                }
            lastActionMinute = date.getMinute();
            lastAction = action;
        }

        public void endShift() {
            if (lastAction.equals(GuardAction.FALL_ASLEEP))
                for (int minute = lastActionMinute; minute < 59; minute++) {
                    sleepingMinues.add(minute);
                }
        }
    }
}
