import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class User implements IUser {
    // List<IEvent> events
    // Integer userID
    // String userName
    
    private List<IEvent> events;
    private Integer userID;
    private String userName;
    
    @Override
    public void userInit() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean addEvent(String startTime, String endTime, String eventName, List<Integer> listAtendees) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteEvent(String editField, Object editResult) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void printEvent(String eventName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public IEvent nextEvent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void viewCalendarByDay(Calendar startDate, int userID) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void viewCalendarByWeek(Calendar startDate, int userID) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Calendar[]> getFreeTime(Calendar startTime, Calendar endTime) {
        // if the start time is after the end time or list of users is empty, return null
        if (startTime.after(endTime)) {
            return null;
        }
        List<Calendar[]> list = new ArrayList<>();
        
        // find the event at the start time
        int currIndex = 0;
        for (IEvent event: events) {
            if (event.getEndTime().after(startTime)) {
                currIndex = events.indexOf(event);
                break;
            }
        }
        
        // if the start time of the first event is after the start time input, 
        // add the free time before the first event
        if (events.get(currIndex).getStartTime().after(startTime)) {
            Calendar[] tuple = {startTime, events.get(currIndex).getStartTime()};
            list.add(tuple);
        }
        int nextIndex = currIndex + 1;
        
        // while the ending time of next event is after input end time, enter loop
        while (events.get(nextIndex).getEndTime().after(endTime)) {
            
            // if the start time of next event is after input end time, add free time
            // between current event and end time
            if (events.get(nextIndex).getStartTime().after(endTime)) {
                Calendar[] tuple = {events.get(currIndex).getEndTime(), endTime};
                list.add(tuple);
                
                // otherwise, add the event between curr and next event
            } else {
                Calendar[] tuple = {events.get(currIndex).getEndTime(), 
                        events.get(nextIndex).getStartTime()};
                list.add(tuple);
            }
            
            // update indexes
            currIndex++;
            nextIndex++;
        }
        
        return list;
    }

}
