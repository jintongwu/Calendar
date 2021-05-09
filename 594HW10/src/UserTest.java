import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    @Test
    public void testgetAllFreeTime() {
        IUser user = new User("amy", 1, 8, 18);
        user.addEvent("2021-5-9-9-0", "2021-5-9-10-0", "yoga", null);
        user.addEvent("2021-5-9-11-0", "2021-5-9-16-0", "work", null);
        System.out.println(user.getEvents().get(0).getStartTime());
        Calendar t1 = new GregorianCalendar(2021, 5 , 9, 7, 0);
        Calendar t2 = new GregorianCalendar(2021, 5 , 9, 19, 0);
        List<Calendar[]> l = user.getAllFreeTime(t1, t2);
        
//        assertEquals(7, l.get(0)[0].get(Calendar.HOUR_OF_DAY));
//        assertEquals(9, l.get(0)[1].get(Calendar.HOUR_OF_DAY));
    }

}
