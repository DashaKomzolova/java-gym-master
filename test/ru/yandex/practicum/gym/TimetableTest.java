package ru.yandex.practicum.gym;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Collection<TrainingSession> trainingSessionsMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> trainingSessionsMondayList = new ArrayList<>(trainingSessionsMonday);
        Assertions.assertEquals(singleTrainingSession, trainingSessionsMondayList.get(0));

        //Проверить, что за вторник не вернулось занятий
        Collection<TrainingSession> trainingSessionsTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertNull(trainingSessionsTuesday);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Collection<TrainingSession> trainingSessionsMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> trainingSessionsMondayList = new ArrayList<>(trainingSessionsMonday);
        Assertions.assertEquals(mondayChildTrainingSession, trainingSessionsMondayList.get(0));

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Collection<TrainingSession> trainingSessionsThursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        List<TrainingSession> trainingSessionsThursdayList = new ArrayList<>(trainingSessionsThursday);
        Assertions.assertEquals(thursdayChildTrainingSession, trainingSessionsThursdayList.get(0));
        Assertions.assertEquals(thursdayAdultTrainingSession, trainingSessionsThursdayList.get(1));

        // Проверить, что за вторник не вернулось занятий
        Collection<TrainingSession> trainingSessionsTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertNull(trainingSessionsTuesday);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Collection<TrainingSession> trainingSessionsMonday = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                        new TimeOfDay(13, 0));
        List<TrainingSession> trainingSessionsMondayList = new ArrayList<>(trainingSessionsMonday);
        Assertions.assertEquals(singleTrainingSession, trainingSessionsMondayList.get(0));

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14,
                                                                                            0)).isEmpty());
    }

    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForEmptyTimeTableDaysOnly() {
        Timetable timetable = new Timetable();

        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));
    }

    @Test
    void testGetTrainingSessionsForEmptyTimeTable() {
        Timetable timetable = new Timetable();

        Assertions.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14,
                0)));
    }

    @Test
    void testGetCountByCoachesOneCoach() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assert.assertEquals(timetable.getCountByCoaches().get(0).getCount(), 4);
    }

    @Test
    void testGetCountByCoachesMultipleCoach() {
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Калашникова", "Анастасия", "Николаевна");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach2,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assert.assertEquals(timetable.getCountByCoaches().get(0).getCount(), 3);
        Assert.assertEquals(timetable.getCountByCoaches().get(1).getCount(), 1);
    }
}
