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
    public IUser addUser(String name, int id) {
        IUser user = new User(name, id);
        return user;
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
    public List<Calendar[]> findOverlap(List<Calendar[]> c1, List<Calendar[]> c2) {
        List<Calendar[]> list = new ArrayList<>();
        
        // loop through calendar 1 slots
        for (int i = 0; i < c1.size(); i++) {
            
            // get start and end time for index i slot in calendar 1
            Calendar start = c1.get(i)[0];
            Calendar end = c1.get(i)[1];
            
            // find calendar 2 slot with end time after slot i
            int index = 0;
            while(c2.get(index)[1].before(start)) {
                index++;
            }
            
            // if c2 event starts earlier, add slot starting the start time of slot i
            if (c2.get(index)[0].before(start)) {
                Calendar[] tuple = {start, c2.get(index)[1]};
                list.add(tuple);
            }
            
            index++;
            
            while (c2.get(index)[0].before(end)) {
                if (c2.get(index)[1].after(end)) {
                    Calendar[] tuple = {c2.get(index)[0], end};
                    list.add(tuple);
                } else {
                    list.add(c2.get(index));
                }
                index++;
            }
        }
        
        
        return list;
    }

    @Override
    public List<Calendar[]> findCommonMeetingTime(Calendar startTime, Calendar endTime, List<IUser> users) {
        // if list of users is empty, return null
        if (users.isEmpty()) {
            return null;
        }
        List<Calendar[]> fullList = users.get(0).getAllFreeTime(startTime, endTime);
        List<Calendar[]> list = users.get(0).getAvailableTime(fullList);
        for (int i = 1; i < users.size(); i++) {
            List<Calendar[]> fullList2 = users.get(i).getAllFreeTime(startTime, endTime);
            List<Calendar[]> list2 = users.get(i).getAvailableTime(fullList2);
            list = this.findOverlap(list, list2);
            if (list.isEmpty()) {
                break;
            }
        }
        
        return list;
    }

    

}