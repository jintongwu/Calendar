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
        this.events = new LinkedList<IEvent>();
    }
    
    public User (String name, int id, int constraintStart, int constraintEnd) {
        this.userName = name;
        this.userID = id;
        this.events = new LinkedList<IEvent>();
        this.constraintStart = constraintStart;
        this.constraintEnd = constraintEnd;
    }

    @Override
    public int compareTo(IUser o) {
        Integer thisId = this.userID;
        return thisId.compareTo(o.getUserID());
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
                    // when start and end are both the same
                    // new one is added to the second
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
            if (tmp.getEventName().equals(eventName)) {
                this.events.remove(i);
                return true;
            }
            i ++;
        }

        return false;

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
    
    @Override
    public IEvent searchEvent(String name) {
        if (this.events == null || this.events.size() == 0) {
            // no event
            return null;
        }
        
        // find the event and delete and return
        int i = 0;
        IEvent tmp = null;
        while(i < this.events.size()) {
            tmp = this.events.get(i);
            if (tmp.getEventName().equals(name)) {
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
    protected static boolean checkDay(Calendar target, Calendar curr) {
        
        boolean bYear = (target.get(Calendar.YEAR) == curr.get(Calendar.YEAR));
        boolean bMonth = (target.get(Calendar.MONTH) == curr.get(Calendar.MONTH));
        boolean bDay = target.get(Calendar.DAY_OF_MONTH) == curr.get(Calendar.DAY_OF_MONTH);
        
        return bYear && bMonth && bDay;
        
    }
    
    /**
     * Helper function to modify the input time of the viewCalendarByWeek() to the the Monday of that week
     * @param dayInTargetWeek
     */
    protected static void mondayFinder(Calendar dayInTargetWeek) {

        // d = Calendar.get(Calendar.DAY_OF_WEEK)
        // (d + numberOfDaysInAWeek - firstDayOfWeek) % numberOfDaysInAWeek
        int currentDayOfWeek = 
                (dayInTargetWeek.get(Calendar.DAY_OF_WEEK) + 7 
                        - dayInTargetWeek.getFirstDayOfWeek()) % 7;
        dayInTargetWeek.add(Calendar.DAY_OF_YEAR, -currentDayOfWeek);
    }

    @Override
    public int getUserID() {
        int ret = this.userID;
        return ret;
    }

    @Override
    public String getUserName() {
        return this.userName;
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
    
    
    /*****************************************************************************/
    
    /**** SYSTEM.OUT method START *****/

    @Override
    public void printEvent(IEvent event) {   
        System.out.printf("%tR - %tR %s\n", 
                event.getStartTime(), event.getEndTime(), event.getEventName());
    }

    @Override
    public void viewCalendarByDay(Calendar targetDate) {
        System.out.println("------------------------------------------------------");
        System.out.printf("Your Schedule(s) for -- %tA %<tB %<te, %<tY -- are:\n", targetDate);
        System.out.println("------------------------------------------------------");
        
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
        
        System.out.println("------------------------------------------------------");
    }

    @Override
    public void viewCalendarByWeek(Calendar dayInTargetWeek) {
        
        // change target date to the Monday in that week
        mondayFinder(dayInTargetWeek);
        
        // get templates
        String[][] view = ICalendarApp.WEEKVIEW.clone();
        
        // read event string from calendar
        // populate view with HARD-COPYs of event names
        viewHelper(dayInTargetWeek, view);
        
        // get Monday date of the month
        int days = dayInTargetWeek.get(Calendar.DAY_OF_MONTH);
        
        // print header
        System.out.printf("%s\n%60sWeek of %tB %<te, %<tY \n", ICalendarApp.LINESEPARATE, " ", dayInTargetWeek);
        System.out.printf("%s\n%6s | %s - %-12d| %s - %-12d| %s - %-12d| %s - %-12d| %s - %-12d| %s - %-12d| %s - %-12d|\n", ICalendarApp.LINESEPARATE,
                view[0][0], view[0][1], days, view[0][2], days + 1, view[0][3], days + 2, 
                view[0][4], days + 3, view[0][5], days + 4, view[0][6], days + 5, view[0][7], days + 6);
        
        // print event by start time
        for (int i = 1; i < 14; i ++) {
            System.out.printf("%s\n%6s | %-18s| %-18s| %-18s| %-18s| %-18s| %-18s| %-18s|\n", ICalendarApp.LINESEPARATE,
                    view[i][0], view[i][1], view[i][2], view[i][3],
                    view[i][4], view[i][5], view[i][6], view[i][7]);
        }
        System.out.println(ICalendarApp.LINESEPARATE);
        
    }
    
    /**
     * Helper function to add hard-copies of event name within the week calendar view to the view martix
     * @param dayInTargetWeek
     * @param view
     */
    protected void viewHelper(Calendar dayInTargetWeek, String[][] view) {
        
        // create range of find
        Calendar start = (Calendar) dayInTargetWeek.clone();
        start.set(Calendar.HOUR_OF_DAY, 7);
        start.set(Calendar.MINUTE, 59);
        Calendar end = (Calendar) dayInTargetWeek.clone();
        end.set(Calendar.HOUR_OF_DAY, 22);
        end.set(Calendar.MINUTE, 00);
        
        @SuppressWarnings("unchecked")
        LinkedList<String>[][] viewEvents = new LinkedList[14][8];
        
        
        // for each day
        // get names of events for each hour
        // stored in list
        for (int i = 1; i < 8; i ++) {
           for (IEvent e : this.events) {  
               Calendar startTime = e.getStartTime();
               Calendar endTime = e.getEndTime();
//               System.out.println(e.getEventName());
               // add curr events names to the string list of according hours
               // from start to end, both inclusive
               if (startTime.after(start) && startTime.before(end)) {
                   int startH = startTime.get(Calendar.HOUR_OF_DAY);
                   int endH = endTime.get(Calendar.HOUR_OF_DAY);
                   for (int j = startH - 7; j < Math.min(endH - 7, 14); j ++) {
                       if (viewEvents[j][i] == null) {
                           viewEvents[j][i] = new LinkedList<String>();
                       }
                       viewEvents[j][i].add(e.getEventName());
                   }          
               }
           }
           start.add(Calendar.DAY_OF_MONTH, 1);
           end.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        
        // modify view matrix
        for (int i = 1; i < 8; i ++) {
            for (int j = 1; j < 14; j ++) {
                // if there are events at this hour
                if (viewEvents[j][i] != null) {
                    // if only one event
                    if (viewEvents[j][i].size() == 1) {
                        // add "..." if the string to too long to display
                        if (viewEvents[j][i].get(0).length() > 14) {
                            view[j][i] = viewEvents[j][i].get(0).substring(0, 14) + ICalendarApp.etc;
                        } else {
                            view[j][i] = new String(viewEvents[j][i].get(0));
                        }
                    } else {
                        view[j][i] = "" + viewEvents[j][i].size() + " events";
                    }
                }
            }          
         }
    }
    
    /**** SYSTEM.OUT method END *****/
}
