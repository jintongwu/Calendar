import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

public class CalendarAppTest {

    @Test
    public void testCalendarInitNoUser() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        assertEquals(1, app.getNextAvailID());
        assertEquals(0, app.getUsers().size());
    }

    @Test
    public void testCalendarInitAddedUser() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        app.addUser("amy");
        app.addUser("bob");
        assertEquals(3, app.getNextAvailID());
        assertEquals(2, app.getUsers().size());
    }

    @Test
    public void testAddUser() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        app.addUser("amy");
        app.addUser("bob");
        assertEquals(app.getUsers().get(0).getUserName(), "amy");
        assertEquals(app.getUsers().get(0).getUserID(), 1);
        assertEquals(app.getUsers().get(1).getUserID(), 2);
    }

    @Test
    public void testDeleteUserExist() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        app.addUser("amy");
        app.addUser("bob");
        app.addUser("cat");
        boolean deleted = app.deleteUser(2);
        assertEquals(deleted, true);
        assertEquals(2, app.getUsers().size());
        assertEquals("amy", app.getUsers().get(0).getUserName());
        assertEquals("cat", app.getUsers().get(1).getUserName());
    }

    @Test
    public void testDeleteUserNotExist() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        app.addUser("amy");
        app.addUser("bob");
        app.addUser("cat");
        boolean deleted = app.deleteUser(4);
        assertEquals(deleted, false);
    }

    @Test
    public void testFindOverlapEmptyList() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        Calendar t1 = new GregorianCalendar(2021, 5 , 8, 8, 0);
        Calendar t2 = new GregorianCalendar(2021, 5 , 8, 9, 0);
        Calendar[] tuple1 = {t1, t2};
        List<Calendar[]> c1 = new ArrayList<>();
        c1.add(tuple1);
        List<Calendar[]> c2 = new ArrayList<>();
        List<Calendar[]> overlap = app.findOverlap(c1, c2);
        assertEquals(0, overlap.size());
    }

    @Test
    public void testFindOverlap1() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        Calendar t1 = new GregorianCalendar(2021, 5 , 8, 8, 0);
        Calendar t2 = new GregorianCalendar(2021, 5 , 8, 9, 0);

        Calendar t3 = new GregorianCalendar(2021, 5 , 8, 7, 0);
        Calendar t4 = new GregorianCalendar(2021, 5 , 8, 12, 0);

        Calendar[] tuple1 = {t1, t2};
        Calendar[] tuple2 = {t3, t4};
        List<Calendar[]> c1 = new ArrayList<>();
        c1.add(tuple1);
        List<Calendar[]> c2 = new ArrayList<>();
        c2.add(tuple2);
        List<Calendar[]> overlap = app.findOverlap(c1, c2);
        assertEquals(8, overlap.get(0)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(9, overlap.get(0)[1].get(Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testFindOverlap2() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        Calendar t1 = new GregorianCalendar(2021, 5 , 8, 8, 0);
        Calendar t2 = new GregorianCalendar(2021, 5 , 8, 10, 0);

        Calendar t3 = new GregorianCalendar(2021, 5 , 8, 7, 0);
        Calendar t4 = new GregorianCalendar(2021, 5 , 8, 9, 0);

        Calendar[] tuple1 = {t1, t2};
        Calendar[] tuple2 = {t3, t4};
        List<Calendar[]> c1 = new ArrayList<>();
        c1.add(tuple1);
        List<Calendar[]> c2 = new ArrayList<>();
        c2.add(tuple2);
        List<Calendar[]> overlap = app.findOverlap(c1, c2);
        assertEquals(8, overlap.get(0)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(9, overlap.get(0)[1].get(Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testFindOverlap3() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        Calendar t1 = new GregorianCalendar(2021, 5 , 8, 8, 0);
        Calendar t2 = new GregorianCalendar(2021, 5 , 8, 16, 0);

        Calendar t3 = new GregorianCalendar(2021, 5 , 8, 7, 0);
        Calendar t4 = new GregorianCalendar(2021, 5 , 8, 9, 0);

        Calendar t5 = new GregorianCalendar(2021, 5 , 8, 12, 0);
        Calendar t6 = new GregorianCalendar(2021, 5 , 8, 16, 0);

        Calendar[] tuple1 = {t1, t2};
        Calendar[] tuple2 = {t3, t4};
        Calendar[] tuple3 = {t5, t6};
        List<Calendar[]> c1 = new ArrayList<>();
        c1.add(tuple1);
        List<Calendar[]> c2 = new ArrayList<>();
        c2.add(tuple2);
        c2.add(tuple3);

        List<Calendar[]> overlap = app.findOverlap(c1, c2);
        assertEquals(8, overlap.get(0)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(9, overlap.get(0)[1].get(Calendar.HOUR_OF_DAY));

        assertEquals(12, overlap.get(1)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(16, overlap.get(1)[1].get(Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testFindOverlap4() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        Calendar t1 = new GregorianCalendar(2021, 5 , 8, 8, 0);
        Calendar t2 = new GregorianCalendar(2021, 5 , 8, 16, 0);

        Calendar t3 = new GregorianCalendar(2021, 5 , 8, 7, 0);
        Calendar t4 = new GregorianCalendar(2021, 5 , 8, 9, 0);

        Calendar t5 = new GregorianCalendar(2021, 5 , 8, 12, 0);
        Calendar t6 = new GregorianCalendar(2021, 5 , 8, 18, 0);

        Calendar t7 = new GregorianCalendar(2021, 5 , 8, 17, 0);
        Calendar t8 = new GregorianCalendar(2021, 5 , 8, 19, 0);

        Calendar[] tuple1 = {t1, t2};
        Calendar[] tuple2 = {t3, t4};
        Calendar[] tuple3 = {t5, t6};
        Calendar[] tuple4 = {t7, t8};
        List<Calendar[]> c1 = new ArrayList<>();
        c1.add(tuple1);
        c1.add(tuple4);
        List<Calendar[]> c2 = new ArrayList<>();
        c2.add(tuple2);
        c2.add(tuple3);

        List<Calendar[]> overlap = app.findOverlap(c1, c2);
        assertEquals(8, overlap.get(0)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(9, overlap.get(0)[1].get(Calendar.HOUR_OF_DAY));

        assertEquals(12, overlap.get(1)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(16, overlap.get(1)[1].get(Calendar.HOUR_OF_DAY));

        assertEquals(17, overlap.get(2)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(18, overlap.get(2)[1].get(Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testCommonMeetingTime() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        IUser user1 = app.addUser("amy");
        IUser user2 = app.addUser("bob");

        user1.addEvent("2021-5-9-9-0", "2021-5-9-10-0", "yoga", null);
        user1.addEvent("2021-5-9-13-0", "2021-5-9-14-0", "work", null);
        user1.addEvent("2021-5-9-17-0", "2021-5-9-20-0", "wind down", null);
        user1.editStartConstraint(6);
        user1.editEndConstraint(21);

        user2.addEvent("2021-5-9-10-0", "2021-5-9-11-0", "yoga", null);
        user2.addEvent("2021-5-9-16-0", "2021-5-9-20-0", "wind down", null);
        user2.editStartConstraint(6);
        user2.editEndConstraint(20);

        Calendar t1 = new GregorianCalendar(2021, 4, 9, 9, 0);
        Calendar t2 = new GregorianCalendar(2021, 4, 9, 15, 0);



        List<Calendar[]> testList = app.findCommonMeetingTime(t1, t2);


        assertEquals(11, testList.get(0)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(13, testList.get(0)[1].get(Calendar.HOUR_OF_DAY));

        assertEquals(14, testList.get(1)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(15, testList.get(1)[1].get(Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testCommonMeetingTime2() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        IUser user1 = app.addUser("amy");
        IUser user2 = app.addUser("bob");

        user1.addEvent("2021-5-10-10-0", "2021-5-10-13-0", "yoga", null);

        user2.addEvent("2021-5-10-12-0", "2021-5-10-15-0", "yoga", null);

        Calendar t1 = new GregorianCalendar(2021, 4, 10, 8, 0);
        Calendar t2 = new GregorianCalendar(2021, 4, 10, 20, 0);



        List<Calendar[]> testList = app.findCommonMeetingTime(t1, t2);


        assertEquals(8, testList.get(0)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(10, testList.get(0)[1].get(Calendar.HOUR_OF_DAY));

        assertEquals(15, testList.get(1)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(20, testList.get(1)[1].get(Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testCommonMeetingTime3() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        IUser user1 = app.addUser("amy");
        IUser user2 = app.addUser("bob");

        user1.editStartConstraint(8);
        user1.editEndConstraint(23);

        user1.addEvent("2021-5-10-8-0", "2021-5-10-9-0", "yoga", null);

        user2.addEvent("2021-5-10-22-0", "2021-5-11-2-0", "yoga", null);

        Calendar t1 = new GregorianCalendar(2021, 4, 10, 7, 0);
        Calendar t2 = new GregorianCalendar(2021, 4, 11, 1, 0);


        List<Calendar[]> testList = app.findCommonMeetingTime(t1, t2);

        assertEquals(9, testList.get(0)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(22, testList.get(0)[1].get(Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testCommonMeetingTime4() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        IUser user1 = app.addUser("amy");
        app.addUser("bob");

        user1.editStartConstraint(8);
        user1.editEndConstraint(23);

        user1.addEvent("2021-5-10-12-0", "2021-5-10-15-0", "yoga", null);
        user1.addEvent("2021-5-10-15-0", "2021-5-10-20-0", "work", null);

        Calendar t1 = new GregorianCalendar(2021, 4, 10, 8, 0);
        Calendar t2 = new GregorianCalendar(2021, 4, 10, 17, 0);

        List<Calendar[]> testList = app.findCommonMeetingTime(t1, t2);

        assertEquals(8, testList.get(0)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(12, testList.get(0)[1].get(Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testParseAttendee() {
        ICalendarApp app = new CalendarApp();
        app.calendarInit();
        app.addUser("Jintong");
        app.addUser("Shuke");

        List<IUser> attendee = app.parseAttendee("Jintong-Shuke-RANDOM");
        assertEquals(3, attendee.size());
        assertEquals(1, (int) attendee.get(0).getUserID());
        assertEquals(2, (int) attendee.get(1).getUserID());
        assertEquals(3, (int) attendee.get(2).getUserID());
    }

}
