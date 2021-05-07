import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarApp implements ICalendarApp {
        
    private List<IUser> users;
    private int nextAvailID;
    
    @Override
    public void calendarInit() {
        this.users = new ArrayList<IUser>();
        this.nextAvailID = 1;
    }

    @Override
    public boolean addUser(IUser user) {
        // check if the user is already in the list of users
        if (users.contains(user)) {
            return false;
        }
        // if not add the user into the list
        users.add(user);
        return true;
    }

    @Override
    public boolean deleteUser(int id) {
        // loop to check if there is a user with the target ID
        for (IUser user: users) {
            // if the ID matches, delete this user
            if (user.getUserID() == id) {
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Calendar[]> findCommonMeetingTime(Calendar startTime, Calendar endTime, List<IUser> users) {
        // if list of users is empty, return null
        if (users.isEmpty()) {
            return null;
        }
        
        return null;
    }

}
