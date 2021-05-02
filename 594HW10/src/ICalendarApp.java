import java.util.Calendar;

public interface ICalendarApp {
    
    // field:
    // List<IUser> users
    // Integer nextAvailID
    // Calendar currTime
    
    /**
     * Initialize a calendar application
     */
    public void calendarInit();
    
    /**
     * Create a user for this application
     * @param userName
     * @return The reference to the user created
     */
    public IUser createUser(String userName);
    
    /**
     * Print a day frame view of a user calendar, 
     * starting from 12am of the specified date
     * @param startDate
     * @param userID
     */
    public void viewCalendarByDay(Calendar startDate, int userID);
    
    /**
     * Print a week frame view of a user calendar, 
     * starting from the first of the specified date
     * @param startDate
     * @param userID
     */
    public void viewCalendarByWeek(Calendar startDate, int userID);
}
