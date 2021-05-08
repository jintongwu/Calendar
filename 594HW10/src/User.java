import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class User implements IUser {

    protected List<IEvent> events;
    protected Integer userID;
    protected String userName;
    protected int constraintStart;
    protected int constraintEnd;

    public User (String name, int id) {
        this.userName = name;
        this.userID = id;
        userInit();
    }
    
    public User (String name, int id, int constraintStart, int constraintEnd) {
        this.userName = name;
        this.userID = id;
        userInit();
        this.constraintStart = constraintStart;
        this.constraintEnd = constraintEnd;
    }

    @Override
    public int compareTo(IUser o) {
        Integer thisId = this.userID;
        return thisId.compareTo(o.getUserID());
    }

    @Override
    public void userInit() {
        this.events = new LinkedList<IEvent>();
    }

    @Override
    public boolean addEvent(String startTime, String endTime, String eventName, List<Integer> listAttendees) {
        try {
            IEvent event = new Event(startTime, endTime, eventName, listAttendees);

            if (this.events.size() == 0) {
                this.events.add(event);
            } else {
                // insert at the first position we encounter a event starts
                // later than the event we want to insert
                
                // edge case: 
                // when the start time is the same
                // put the one that ends first to the front
                int i = 0;
                IEvent tmp = null;
                while(i < this.events.size()) {
                    tmp = this.events.get(i);
                    if (tmp.getStartTime().after(event.getStartTime()) &&
                            tmp.getEndTime().after(event.getEndTime())) {
                        break;
                    }
                    i ++;
                }

                if (i == this.events.size()) {
                    // adding to end of the list
                    this.events.add(event);
                } else {
                    // adding to middle of the list
                    this.events.add(i, event);
                }
            }
        } catch (IllegalArgumentException e) {
            // when the inputs to event constructor is invalid
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteEvent(String eventName) {
        if (this.events == null || this.events.size() == 0) {
            return false;
        }

        // find the event and delete and return
        int i = 0;
        IEvent tmp = null;
        while(i < this.events.size()) {
            tmp = this.events.get(i);
            if (tmp.getEventName().equals(tmp.getEventName())) {
                this.events.remove(i);
                return true;
            }
            i ++;
        }

        return false;

    }

    @Override
    public void printEvent(IEvent event) {   
        System.out.printf("%tR - %tR %s\n", 
                event.getStartTime(), event.getEndTime(), event.getEventName());
    }

    @Override
    public IEvent nextEvent() {
        if (this.events == null || this.events.size() == 0) {
            // no event
            return null;
        }
        
        Calendar now = Calendar.getInstance();
        
        // find the event and delete and return
        int i = 0;
        IEvent tmp = null;
        while(i < this.events.size()) {
            tmp = this.events.get(i);
            if (tmp.getStartTime().after(now)) {
                return tmp;
            }
            i ++;
        }
        
        // no such event
        return null;
    }
    
    /**
     * Check if two time are one the same day
     * @param target
     * @param curr
     * @return
     */
    private boolean checkDay(Calendar target, Calendar curr) {
        
        boolean bYear = target.get(Calendar.YEAR) == curr.get(Calendar.YEAR);
        boolean bMonth = target.get(Calendar.MONTH) == curr.get(Calendar.MONTH);
        boolean bDay = target.get(Calendar.DAY_OF_MONTH) == curr.get(Calendar.DAY_OF_MONTH);
        
        return bYear && bMonth && bDay;
        
    }

    @Override
    public void viewCalendarByDay(Calendar targetDate) {
        
        System.out.printf("Your Scedule for %tA %<tB  %<te,  %<tY is:\n");
        // find all event on that day
        List<IEvent> day = new LinkedList<>();;
        
        // get all even that starts on target day
        int i = 0;
        IEvent tmp = null;
        while(i < this.events.size()) {
            tmp = this.events.get(i);
            if (checkDay(targetDate, tmp.getStartTime())) {
                day.add(tmp);
            }
            i ++;
        }
        
        if (day.size() == 0) {
            System.out.println("You do not have any event on this day.");
            return;
        }
        
        // print each event
        for (IEvent e : day) {
            printEvent(e);
        }
        
    }

    @Override
    public void viewCalendarByWeek(Calendar startOfWeekDate) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getUserID() {
        int ret = this.userID;
        return ret;
    }

    @Override
    public void editStartConstraint(int startConstraint) {
        this.constraintStart = startConstraint;

    }

    @Override
    public void editEndConstraint(int endConstraint) {
        this.constraintEnd = endConstraint;

    }

    @Override
    public List<IEvent> getEvents() {
        return this.events;
    }

    @Override
    public List<Calendar[]> getAllFreeTime(Calendar startTime, Calendar endTime) {
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
    
    @Override
    public List<Calendar[]> getAvailableTime(List<Calendar[]> l) {
        for (int i = 0; i < l.size(); i++) {
            // get the start and end hour of the i calendar pair
            int startHour = l.get(i)[0].get(Calendar.HOUR_OF_DAY);
            int endHour = l.get(i)[1].get(Calendar.HOUR_OF_DAY);
            
            // check if start time and end time of the slot are in constraint bounds
            boolean startInBound = startHour >= this.constraintStart && startHour < this.constraintEnd;
            boolean endInBound = endHour >= this.constraintStart && endHour < this.constraintEnd;
            
            if (startInBound == false && endInBound == false) { // if either is in bound, remove slot
                l.remove(i);
            } else if (startInBound == true && endInBound == false) { // if start is in bound, reset end time
                l.get(i)[1].set(Calendar.HOUR_OF_DAY, this.constraintEnd);
                l.get(i)[1].set(Calendar.MINUTE, 0);
                l.get(i)[1].set(Calendar.SECOND, 0);
            } else if (startInBound == false && endInBound == true) { // if end is in bound, reset start time
                l.get(i)[0].set(Calendar.HOUR_OF_DAY, this.constraintStart);
                l.get(i)[0].set(Calendar.MINUTE, 0);
                l.get(i)[0].set(Calendar.SECOND, 0);
            }
        }
        return l;
        
    }

}
