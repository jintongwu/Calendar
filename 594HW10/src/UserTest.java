import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    User u;
    User u2;
    @Before
    public void setUp() throws Exception {
        u = new User("Jintong", 10001);
        u2 = new User("Shuke", 10002, 1, 3);
    }

    @Test
    public void testConstructor() {
        assertEquals("Jintong", u.getUserName());
        assertEquals(10001, u.getUserID());
        assertEquals(0, u.getEvents().size());
        assertEquals(1, u2.constraintStart);
        assertEquals(3, u2.constraintEnd);

        u2.editStartConstraint(4);
        u2.editEndConstraint(6);
        assertEquals(4, u2.constraintStart);
        assertEquals(6, u2.constraintEnd);

    }

    @Test
    public void testCompareTo() {
        assertEquals(-1, u.compareTo(u2));
    }


    @Test
    public void testAddEventDiffStart() {
        u.addEvent("2021-5-9-15-0", "2021-5-9-16-0", "testOne", new LinkedList<Integer>());
        u.addEvent("2021-5-9-16-0", "2021-5-9-17-0", "testTwo", new LinkedList<Integer>());


        assertEquals(2, u.getEvents().size());
        assertEquals("testOne", u.getEvents().get(0).getEventName());
        assertEquals("testTwo", u.getEvents().get(1).getEventName());

        u.addEvent("2021-5-9-12-0", "2021-5-9-17-0", "testThree", new LinkedList<Integer>());

        assertEquals(3, u.getEvents().size());
        assertEquals("testThree", u.getEvents().get(0).getEventName());
        assertEquals("testOne", u.getEvents().get(1).getEventName());
        assertEquals("testTwo", u.getEvents().get(2).getEventName());
    }

    @Test
    public void testAddEventIllegalArguement() {
        assertFalse(u.addEvent("sss", "2021-5-9-16-0", "testOne", new LinkedList<Integer>()));
        assertFalse(u.addEvent("2021-5-9-16-0", "2021-5-9-16-0", null, new LinkedList<Integer>()));
    }

    @Test
    public void testAddEventSameStartDiffEnd() {
        u.addEvent("2021-5-9-15-0", "2021-5-9-16-0", "testOne", new LinkedList<Integer>());
        u.addEvent("2021-5-9-15-0", "2021-5-9-17-0", "testTwo", new LinkedList<Integer>());

        assertEquals(2, u.getEvents().size());
        assertEquals("testOne", u.getEvents().get(0).getEventName());
        assertEquals("testTwo", u.getEvents().get(1).getEventName());

    }

    @Test
    public void testAddEventSameStartSameEnd() {
        u.addEvent("2021-5-9-15-0", "2021-5-9-16-0", "testOne", new LinkedList<Integer>());
        u.addEvent("2021-5-9-15-0", "2021-5-9-16-0", "testTwo", new LinkedList<Integer>());

        assertEquals(2, u.getEvents().size());
        assertEquals("testOne", u.getEvents().get(0).getEventName());
        assertEquals("testTwo", u.getEvents().get(1).getEventName());
    }


    @Test
    public void testDeleteEvent() {

        u.addEvent("2021-5-9-15-0", "2021-5-9-16-0", "testOne", new LinkedList<Integer>());
        u.addEvent("2021-5-9-15-0", "2021-5-9-16-0", "testTwo", new LinkedList<Integer>());
        u.addEvent("2021-5-9-16-0", "2021-5-9-18-0", "testThree", new LinkedList<Integer>());
        u.addEvent("2021-5-9-19-0", "2021-5-9-20-0", "testFour", new LinkedList<Integer>());

        assertEquals(4, u.getEvents().size());

        assertEquals("testOne", u.getEvents().get(0).getEventName());
        assertEquals("testTwo", u.getEvents().get(1).getEventName());
        assertEquals("testThree", u.getEvents().get(2).getEventName());
        assertEquals("testFour", u.getEvents().get(3).getEventName());

        assertTrue(u.deleteEvent("testOne"));
        assertFalse(u.deleteEvent("testOne"));
        assertEquals(3, u.getEvents().size());

        assertTrue(u.deleteEvent("testFour"));
        assertFalse(u.deleteEvent("testFour"));
        assertEquals(2, u.getEvents().size());

        assertTrue(u.deleteEvent("testThree"));
        assertFalse(u.deleteEvent("testThree"));
        assertEquals(1, u.getEvents().size());

        assertTrue(u.deleteEvent("testTwo"));
        assertFalse(u.deleteEvent("testTwo"));
        assertEquals(0, u.getEvents().size());


        assertFalse(u.deleteEvent("testOne"));
        assertFalse(u.deleteEvent("testTwo"));
        assertFalse(u.deleteEvent("testThree"));
        assertFalse(u.deleteEvent(null));

    }

    @Test
    public void testNextEvent() {
        assertEquals(0, u.getEvents().size());
        assertNull(u.nextEvent());
        u.addEvent("2001-5-6-12-0", "2010-5-10-16-0", "first", new LinkedList<Integer>());

        // no next event
        assertNull(u.nextEvent());

        // add next event
        u.addEvent("2099-5-10-16-0", "2100-5-11-16-0", "next", new LinkedList<Integer>());

        assertEquals(2, u.getEvents().size());
        assertEquals("first", u.getEvents().get(0).getEventName());
        assertEquals("next", u.getEvents().get(1).getEventName());
        assertEquals("next", u.nextEvent().getEventName());
    }

    @Test
    public void testCheckDay() {
        Calendar cal1 = new GregorianCalendar(2021, 4, 9, 8, 0);
        Calendar cal2 = new GregorianCalendar(2021, 4, 9, 15, 0);
        Calendar cal3 = new GregorianCalendar(2021, 4, 19, 8, 0);

        assertTrue(User.checkDay(cal1, cal2));

        assertFalse(User.checkDay(cal1, cal3));
    }

    @Test
    public void testSearchEvent() {

        assertNull(u.searchEvent("name"));

        u.addEvent("2021-5-9-15-0", "2021-5-9-16-0", "testOne", new LinkedList<Integer>());
        u.addEvent("2021-5-9-15-0", "2021-5-9-16-0", "testTwo", new LinkedList<Integer>());
        u.addEvent("2021-5-9-16-0", "2021-5-9-18-0", "testThree", new LinkedList<Integer>());
        u.addEvent("2021-5-9-19-0", "2021-5-9-20-0", "testFour", new LinkedList<Integer>());
        u.addEvent("2021-5-9-12-0", "2021-5-9-17-0", "testFive", new LinkedList<Integer>());


        assertNull(u.searchEvent("NOTEXIST"));
        assertNull(u.searchEvent(null));
        assertEquals("testFive", u.searchEvent("testFive").getEventName());
        assertEquals("testFour", u.searchEvent("testFour").getEventName());
        assertEquals("testThree", u.searchEvent("testThree").getEventName());
        assertEquals("testTwo", u.searchEvent("testTwo").getEventName());
        assertEquals("testOne", u.searchEvent("testOne").getEventName());

    }

    @Test
    public void testMondayFinder() {
       Calendar cal = new GregorianCalendar(2021, 4, 9, 8, 0);
       cal.setFirstDayOfWeek(Calendar.MONDAY);


       User.mondayFinder(cal);
       assertEquals(Calendar.MONDAY, cal.get(Calendar.DAY_OF_WEEK));
       assertEquals(3, cal.get(Calendar.DAY_OF_MONTH));


       cal.set(Calendar.DAY_OF_MONTH, 8);
       User.mondayFinder(cal);
       assertEquals(Calendar.MONDAY, cal.get(Calendar.DAY_OF_WEEK));
       assertEquals(3, cal.get(Calendar.DAY_OF_MONTH));

       cal.set(Calendar.DAY_OF_MONTH, 7);
       User.mondayFinder(cal);
       assertEquals(Calendar.MONDAY, cal.get(Calendar.DAY_OF_WEEK));
       assertEquals(3, cal.get(Calendar.DAY_OF_MONTH));

       cal.set(Calendar.DAY_OF_MONTH, 6);
       User.mondayFinder(cal);
       assertEquals(Calendar.MONDAY, cal.get(Calendar.DAY_OF_WEEK));
       assertEquals(3, cal.get(Calendar.DAY_OF_MONTH));

       cal.set(Calendar.DAY_OF_MONTH, 5);
       User.mondayFinder(cal);
       assertEquals(Calendar.MONDAY, cal.get(Calendar.DAY_OF_WEEK));
       assertEquals(3, cal.get(Calendar.DAY_OF_MONTH));

       cal.set(Calendar.DAY_OF_MONTH, 4);
       User.mondayFinder(cal);
       assertEquals(Calendar.MONDAY, cal.get(Calendar.DAY_OF_WEEK));
       assertEquals(3, cal.get(Calendar.DAY_OF_MONTH));

       cal.set(Calendar.DAY_OF_MONTH, 3);
       User.mondayFinder(cal);
       assertEquals(Calendar.MONDAY, cal.get(Calendar.DAY_OF_WEEK));
       assertEquals(3, cal.get(Calendar.DAY_OF_MONTH));

    }
    @Test
    public void testgetAllFreeTime1() {
        IUser user = new User("amy", 1, 8, 18);
        user.addEvent("2021-5-9-9-0", "2021-5-9-10-0", "yoga", null);
        user.addEvent("2021-5-9-11-0", "2021-5-9-16-0", "work", null);
        Calendar t1 = new GregorianCalendar(2021, 4 , 9, 7, 0);
        Calendar t2 = new GregorianCalendar(2021, 4 , 9, 19, 0);
        List<Calendar[]> l = user.getAllFreeTime(t1, t2);
        assertEquals(7, l.get(0)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(9, l.get(0)[1].get(Calendar.HOUR_OF_DAY));

        assertEquals(10, l.get(1)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(11, l.get(1)[1].get(Calendar.HOUR_OF_DAY));
        
        assertEquals(16, l.get(2)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(19, l.get(2)[1].get(Calendar.HOUR_OF_DAY));
    }
    
    @Test
    public void testgetAllFreeTime2() {
        IUser user = new User("amy", 1, 8, 18);
        user.addEvent("2021-5-9-9-0", "2021-5-9-10-0", "yoga", null);
        user.addEvent("2021-5-9-11-0", "2021-5-9-16-0", "work", null);
        user.addEvent("2021-5-9-17-0", "2021-5-9-20-0", "wind down", null);
        Calendar t1 = new GregorianCalendar(2021, 4 , 9, 7, 0);
        Calendar t2 = new GregorianCalendar(2021, 4 , 9, 19, 0);
        List<Calendar[]> l = user.getAllFreeTime(t1, t2);
        assertEquals(7, l.get(0)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(9, l.get(0)[1].get(Calendar.HOUR_OF_DAY));

        assertEquals(10, l.get(1)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(11, l.get(1)[1].get(Calendar.HOUR_OF_DAY));
        
        assertEquals(16, l.get(2)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(17, l.get(2)[1].get(Calendar.HOUR_OF_DAY));
    }
    
    @Test
    public void testgetAllFreeTime3() {
        IUser user = new User("amy", 1, 8, 18);
        user.addEvent("2021-5-9-6-0", "2021-5-9-10-0", "yoga", null);
        user.addEvent("2021-5-9-11-0", "2021-5-9-16-0", "work", null);
        user.addEvent("2021-5-9-17-0", "2021-5-9-20-0", "wind down", null);
        Calendar t1 = new GregorianCalendar(2021, 4 , 9, 7, 0);
        Calendar t2 = new GregorianCalendar(2021, 4 , 9, 19, 0);
        List<Calendar[]> l = user.getAllFreeTime(t1, t2);

        assertEquals(10, l.get(0)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(11, l.get(0)[1].get(Calendar.HOUR_OF_DAY));
        
        assertEquals(16, l.get(1)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(17, l.get(1)[1].get(Calendar.HOUR_OF_DAY));
    }
    
    @Test
    public void testGetAvailableTime() {
        IUser user = new User("amy", 1, 8, 18);
        List<Calendar[]> list = new ArrayList<>();
        Calendar t1 = new GregorianCalendar(2021, 4 , 9, 7, 0);
        Calendar t2 = new GregorianCalendar(2021, 4 , 9, 10, 0);
        Calendar[] tuple1 = {t1, t2};
        
        Calendar t3 = new GregorianCalendar(2021, 4 , 9, 14, 0);
        Calendar t4 = new GregorianCalendar(2021, 4 , 9, 19, 0);
        Calendar[] tuple2 = {t3, t4};
        
        list.add(tuple1);
        list.add(tuple2);
        
        List<Calendar[]> avail = user.getAvailableTime(list);
        
        assertEquals(8, avail.get(0)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(10, avail.get(0)[1].get(Calendar.HOUR_OF_DAY));
        
        assertEquals(14, avail.get(1)[0].get(Calendar.HOUR_OF_DAY));
        assertEquals(18, avail.get(1)[1].get(Calendar.HOUR_OF_DAY));
    }
}
