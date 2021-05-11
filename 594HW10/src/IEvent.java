import java.util.*;

public interface IEvent extends Comparable<IEvent> {
    // field
    // String eventName
    // String description
    // List<Integer> attendee
    // Calendar startTime
    // Calendar endTime

    /**
     * Change the short name of the event
     *
     * @param name
     * @return
     */
    public void editEventName(String name);

    /**
     * Change the long description of the event
     *
     * @param description
     * @return
     */
    public void editDescription(String description);

    /**
     * Get the list of attendee of the event
     *
     * @return The list of attendee ID
     */
    public List<Integer> getAttendee();

    /**
     * Edit the startTime of the event
     *
     * @param time
     * @return true if the edit is successful false if error has occurred (e.g.
     *         startTime is later than endTime)
     */
    public boolean changeStartTime(String time);

    /**
     * change the end time of the the event
     *
     * @param time
     * @return true if the edit is successful, false if error
     */
    public boolean changeEndTime(String time);

    /**
     * change the duration of the event by modifying the end time of the event
     *
     * @param hours
     * @param mins
     * @return true if the edit is successful, false if error have occur and/or the
     *         input is not valid
     */
    public boolean changeDuration(int hours, int mins);

    /**
     * Get the start time of this event
     * 
     * @return a Calendar object representing the start time
     */
    public Calendar getStartTime();

    /**
     * Get the end time of the event
     * 
     * @return a Calendar object representing the end time
     */
    public Calendar getEndTime();

    /**
     * Get the name of the event
     * 
     * @return
     */
    public String getEventName();

}
