import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class User implements IUser {

    protected List<IEvent> events;
    protected Integer userID;
    protected String userName;

    public User (String name, int id) {
        this.userName = name;
        this.userID = id;
        userInit();
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
                int i = 0;
                IEvent tmp = null;
                while(i < this.events.size()) {
                    tmp = this.events.get(i);
                    if (tmp.getStartTime().after(event.getStartTime())) {
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
    public int getUserID() {
        int ret = this.userID;
        return ret;
    }

    @Override
    public void addStartConstraint(Calendar startConstraint) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addEndConstraint(Calendar endConstraint) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<IEvent> getEvents() {
        return this.events;
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
