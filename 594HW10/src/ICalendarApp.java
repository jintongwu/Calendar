import java.util.Calendar;
import java.util.List;

public interface ICalendarApp {

    // field:
    // List<IUser> users
    // Integer nextAvailID
    // Calendar currTime

    // TODO: Eric Suggestions
    // Read calendar automatically
    // Constraints for common meeting time
    // Add event to everyone’s calendar
    // File input to initialize calendar
    // Scanner for user input in console

    // TODO:
    
    // Shuke: Implement CalendarApp
    // Jintong: Implement User + Event

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
    public boolean addUser(IUser user, int id);

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
    public List<Calendar[]> findCommonMeetingTime(Calendar startTime, Calendar endTime, List<IUser> users);

}
