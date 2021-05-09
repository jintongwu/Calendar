import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class EventTest {
    Event e;
    @Before
    public void setUp() throws Exception {
        e = new Event("2021-5-9-10-00", "2021-5-9-12-00", "test event", new LinkedList<Integer>());
    }

    @Test
    public void testConstructor() {
        
        assertEquals(2021, e.getStartTime().get(Calendar.YEAR));
        assertEquals(2021, e.getEndTime().get(Calendar.YEAR));
        assertEquals(4, e.getStartTime().get(Calendar.MONTH));
        assertEquals(4, e.getEndTime().get(Calendar.MONTH));
        assertEquals(9, e.getStartTime().get(Calendar.DAY_OF_MONTH));
        assertEquals(9, e.getEndTime().get(Calendar.DAY_OF_MONTH));
        assertEquals(10, e.getStartTime().get(Calendar.HOUR_OF_DAY));
        assertEquals(12, e.getEndTime().get(Calendar.HOUR_OF_DAY));
        assertEquals(0, e.getStartTime().get(Calendar.MINUTE));
        assertEquals(0, e.getEndTime().get(Calendar.MINUTE));
        
        assertEquals("test event", e.getEventName());
        assertEquals("", e.description);
        assertEquals(0, e.getAttendee().size());
        
    }
    
    @Test
    public void testConstructorIllegalArguemnt() {
        
        boolean caught = false;
        try {
            e = new Event("1999-5-9-10-00", "2021-5-9-12-00", "test event", new LinkedList<Integer>());
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        assertTrue(caught);
        caught = false;
        
        try {
            e = new Event("2021-5-9-10-00", "2111-5-9-12-00", "test event", new LinkedList<Integer>());
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        assertTrue(caught);
        caught = false;
        
        try {
            e = new Event(null, null, "test event", new LinkedList<Integer>());
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        assertTrue(caught);
        caught = false;
        
        try {
            e = new Event("2012-5", "2011-5-9-12-00", "test event", new LinkedList<Integer>());
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        assertTrue(caught);
        caught = false;
        
        try {
            e = new Event("skkk", "2011-5-9-12-00", "test event", new LinkedList<Integer>());
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        assertTrue(caught);
        caught = false;
        
        try {
            e = new Event("2011-13-9-10-00", "2011-5-9-12-00", "test event", new LinkedList<Integer>());
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        assertTrue(caught);
        caught = false;
        
        try {
            e = new Event("2011-1-32-10-00", "2011-5-9-12-00", "test event", new LinkedList<Integer>());
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        assertTrue(caught);
        caught = false;
        
        try {
            e = new Event("2011-1-30-24-00", "2011-5-9-12-00", "test event", new LinkedList<Integer>());
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        assertTrue(caught);
        caught = false;
       
        try {
            e = new Event("2011-1-30-1-60", "2011-5-9-12-00", "test event", new LinkedList<Integer>());
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        assertTrue(caught);
        caught = false;
        
        
        try {
            e = new Event("2021-5-10-10-00", "2021-5-9-12-00", "test event", new LinkedList<Integer>());
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        assertTrue(caught);
        caught = false;
        
        try {
            e = new Event("2021-5-8-10-00", "2021-5-9-12-00", null, new LinkedList<Integer>());
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        assertTrue(caught);
        caught = false;
        
        try {
            e = new Event("2021-5-8-10-00", "2021-5-9-12-00", "", new LinkedList<Integer>());
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        assertTrue(caught);
        caught = false;

    }
    
    @Test
    public void testEditName() {
        
        e.editEventName("testEvent");
        e.editDescription("testDescription");

        assertEquals("testEvent", e.getEventName());
        assertEquals("testDescription", e.description);
    }
    
    @Test
    public void testGetAttendee() {
        
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(10003);
        list.add(10004);
        list.add(10005);
        list.add(10006);
        
        e = new Event("2021-5-9-10-00", "2021-5-9-12-00", "test event", list);

        assertEquals(4, e.getAttendee().size());
        assertEquals(10003, (int) e.getAttendee().get(0));
        assertEquals(10004, (int) e.getAttendee().get(1));
        assertEquals(10005, (int) e.getAttendee().get(2));
        assertEquals(10006, (int) e.getAttendee().get(3));
        
    }
    
    @Test
    public void testEditTime() {
        
        assertTrue(e.changeStartTime("2021-5-8-10-00"));
        assertTrue(e.changeEndTime("2021-5-10-10-00"));
        
        assertFalse(e.changeStartTime("2101-5-8-10-00"));
        assertFalse(e.changeEndTime("2101-5-10-10-00"));
        assertFalse(e.changeStartTime("2021-5-11-10-00"));
        assertFalse(e.changeEndTime("2021-5-7-10-00"));
        assertFalse(e.changeStartTime("ddd"));
        assertFalse(e.changeEndTime("dddf"));   
        assertFalse(e.changeStartTime("2021-ee-11-10-00"));
        assertFalse(e.changeEndTime("2021-5-ee-10-00"));

    }
    
    @Test
    public void testChangeDuration() {
        assertEquals(10, e.getStartTime().get(Calendar.HOUR_OF_DAY));
        assertEquals(12, e.getEndTime().get(Calendar.HOUR_OF_DAY));
        assertTrue(e.changeDuration(1, 0));
        assertEquals(10, e.getStartTime().get(Calendar.HOUR_OF_DAY));
        assertEquals(11, e.getEndTime().get(Calendar.HOUR_OF_DAY));

        
        assertFalse(e.changeDuration(0, 0));
        assertFalse(e.changeDuration(-1, 0));
    }
    
    @Test
    public void testCompareTo() {
        Event e2 = new Event("2021-5-9-10-00", "2021-5-9-12-00", "test event", new LinkedList<Integer>());
        Event e3 = new Event("2021-5-9-10-00", "2021-5-10-12-00", "test event", new LinkedList<Integer>());
        Event e4 = new Event("2021-5-8-10-00", "2021-5-9-12-00", "test event", new LinkedList<Integer>());
        assertEquals(0, e2.compareTo(e));
        assertEquals(1, e3.compareTo(e));
        assertEquals(-1, e4.compareTo(e));
    }

}
