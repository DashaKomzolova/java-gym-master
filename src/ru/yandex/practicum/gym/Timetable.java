package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private TreeMap<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable;

    public Timetable() {
        this.timetable = new TreeMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek =  trainingSession.getDayOfWeek();
        TreeMap<TimeOfDay, TrainingSession> trainingSessionOfCurrentDay  = timetable.get(dayOfWeek);

        if (trainingSessionOfCurrentDay == null) {
            trainingSessionOfCurrentDay = new TreeMap<>();
            timetable.put(dayOfWeek, trainingSessionOfCurrentDay);
        }

        trainingSessionOfCurrentDay.put(trainingSession.getTimeOfDay(), trainingSession);
    }

    public Collection<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, TrainingSession> trainingSessionOfCurrentDay = timetable.get(dayOfWeek);
        if (trainingSessionOfCurrentDay == null) {
            return null;
        }
        return trainingSessionOfCurrentDay.values();
    }

    public Collection<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek,
                                                                                TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, TrainingSession> trainingSessionOfCurrentDay = timetable.get(dayOfWeek);
        if (trainingSessionOfCurrentDay == null) {
            return null;
        }

        return trainingSessionOfCurrentDay.tailMap(timeOfDay).values();
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> numberOfTrainingSessionsForCoaches = new HashMap<>();

        for (DayOfWeek e : timetable.keySet()) {
            TreeMap<TimeOfDay, TrainingSession> currentDay = timetable.get(e);
            for (TrainingSession t : currentDay.values()) {
                Coach coach = t.getCoach();
                if (!numberOfTrainingSessionsForCoaches.containsKey(coach)) {
                    numberOfTrainingSessionsForCoaches.put(coach, 1);
                } else {
                    int temp = numberOfTrainingSessionsForCoaches.get(coach);
                    numberOfTrainingSessionsForCoaches.replace(coach, temp + 1);
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Coach coach : numberOfTrainingSessionsForCoaches.keySet()) {
            int count = numberOfTrainingSessionsForCoaches.get(coach);
            result.add(new CounterOfTrainings(coach, count));
        }

        Collections.sort(result);

        return result;
    }
}
