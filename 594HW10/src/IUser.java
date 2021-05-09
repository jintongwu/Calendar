import java.util.*;

public interface IUser extends Comparable<IUser> {

    // field:
    // List<IEvent> events
    // Integer userID
    // String userName


    /**
     * Enable user to add an event, return true if the event is successfully added,
     * false if the event has duplicated name or has conflicts with another event
     * with one or multiple attendees
     *
     * input startTime and endTime should be in format: year-month-day-hour-min
     * (e.g. 2021-5-1-10-22)
     *
     * @param date
     * @param startTime
     * @return successful in adding event?
     */
    public boolean addEvent(String startTime, String endTime, String eventName, List<Integer> listAttendees);

    /**
     * Enable user to delete an event identified by name return true if the event is
     * successfully deleted false if the event name does not exist
     *
     * @return successful in deleting event?
     */
    public boolean deleteEvent(String eventName);

    /**
     * Enable user to print an event including: the event name, date, day of week,
     * start and end time notes and a list of attendees
     *
     * @param event
     */
    public void printEvent(IEvent event);

    /**
     *
     * @return the next upcoming event based on the time right now
     */
    public IEvent nextEvent();


    /**
     * Print a day frame view of a user calendar,
     * starting from 12am of the specified date
     * @param startDate
     * @param userID
     */
    public void viewCalendarByDay(Calendar targetDate);

    /**
     * Print a week frame view of a user calendar,
     * starting from the first of the specified date
     * @param startDate
     * @param userID
     */
    public void viewCalendarByWeek(Calendar startOfWeekDate);

    /**
     * Return the UserId
     * @param id
     * @return
     */
    public int getUserID();


    public void editStartConstraint(int startConstraint);

    public void editEndConstraint(int endConstraint);

    public List<IEvent> getEvents();
    
    
    /**
     * get all the free time the user has, which is any time the user
     * does not have an event
     * @param startTime
     * @param endTime
     * @return a list of calendar pairs or start and end times
     */
    public List<Calendar[]> getAllFreeTime(Calendar startTime, Calendar endTime);
    
    /**
     * get all the available time, taking into consideration the time
     * constraints the user set
     * @param a list of free time
     * @return a list of available time
     */
    
    public List<Calendar[]> getAvailableTime(List<Calendar[]> l);
}
