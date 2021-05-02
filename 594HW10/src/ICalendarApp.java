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
     * Delete a user from this application
     * @param id 
     * @param userName
     */
    public void deleteUser(int id, String userName);
    

}
