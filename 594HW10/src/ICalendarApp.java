import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public interface ICalendarApp {

    // field:
    // List<IUser> users
    // Integer nextAvailID
    // Calendar currTime

    final static Calendar MINCAL = new GregorianCalendar(2000, 0, 1, 0, 0);
    final static Calendar MAXCAL = new GregorianCalendar(2100, 11, 31, 23, 59);

    // view templates
    final static String ETC = "...";
    final static String LINESEPARATE = "-----------------------------" + "--------------------------------"
            + "--------------------------------" + "------------------------------------------------------";
    final static String[][] WEEKVIEW = new String[][] { { "", "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" },
            { "  8 AM", "", "", "", "", "", "", "" }, { "  9 AM", "", "", "", "", "", "", "" },
            { " 10 AM", "", "", "", "", "", "", "" }, { " 11 AM", "", "", "", "", "", "", "" },
            { " 12 PM", "", "", "", "", "", "", "" }, { "  1 PM", "", "", "", "", "", "", "" },
            { "  2 PM", "", "", "", "", "", "", "" }, { "  3 PM", "", "", "", "", "", "", "" },
            { "  4 PM", "", "", "", "", "", "", "" }, { "  5 PM", "", "", "", "", "", "", "" },
            { "  6 PM", "", "", "", "", "", "", "" }, { "  7 PM", "", "", "", "", "", "", "" },
            { "  8 PM", "", "", "", "", "", "", "" } };

    /**
     * Initialize a calendar application
     */
    public void calendarInit();

    /**
     * Create an user calendar for this application
     * 
     * @param the user that is to be added to the calendar app
     * @return whether this user is added successfully
     */
    public IUser addUser(String name);

    /**
     * Delete an user calendar from this application
     * 
     * @param id
     * @return whether this user is deleted successfully
     */
    public boolean deleteUser(int id);

    public List<Calendar[]> findOverlap(List<Calendar[]> c1, List<Calendar[]> c2);

    /**
     * find a common meeting time for a list of user given a a time frame from
     * startTime to endTime
     * 
     * @param startTime
     * @param endTime
     * @param users
     * @return a list of calendar array, each array contains start time and end time
     */
    public List<Calendar[]> findCommonMeetingTime(Calendar startTime, Calendar endTime);

    /**
     * provide the next available ID
     * 
     * @return ID
     */
    public int getNextAvailID();

    /**
     * provide a list of users in this calendar app
     * 
     * @return a list of users
     */
    public List<IUser> getUsers();

    /**
     * When a string of attendee names given, return a list of users who are the
     * attendees
     * 
     * @param a string that contains the name of the attendees
     * @return a list of users who are the attendees
     */
    public List<IUser> parseAttendee(String input);
}
