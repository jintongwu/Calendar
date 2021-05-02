import java.util.*;

public interface IUser {

    // field:
    // List<IEvent> events
    // Integer userID
    // String userName

    /**
     * initialize a calendar for this user
     */
    public void userInit();

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
    public boolean addEvent(String startTime, String endTime, String eventName, List<Integer> listAtendees);

    /**
     * Enable user to delete an event identified by name return true if the event is
     * successfully deleted false if the event name does not exist
     * 
     * @param editField
     * @param editResult
     * @return successful in deleting event?
     */
    public boolean deleteEvent(String editField, Object editResult);

    /**
     * Enable user to print an event including: the event name, date, day of week,
     * start and end time notes and a list of attendees
     * 
     * @param event
     */
    public void printEvent(String eventName);

    /**
     * 
     * @return the next upcoming event based on the time right now
     */
    public IEvent nextEvent();

}
