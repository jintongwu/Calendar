import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class Event implements IEvent {
    // field
    protected String eventName;
    protected String description;
    protected Calendar startTime;
    protected Calendar endTime;
    protected List<Integer> attendeeIDs;


    public Event(String startTime, String endTime, String eventName, 
            List<Integer> listAttendees) throws IllegalArgumentException {
        
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException(
                    "Invalid Arguemnt: startTime and endTime must be speficified");
        }
        
        this.startTime = parseTime(startTime);
        this.endTime = parseTime(endTime);
        
        // Illegal Arguments
        if (this.startTime == null || this.endTime == null) {
            throw new IllegalArgumentException(
                    "Invalid Arguemnt: Input time is not in valid format, "
                    + "please enter year-month-day-hour(0-24)-minute, separated by dash -");
        }
        if (!isValidTime(this.startTime) || !isValidTime(this.endTime)) {
            throw new IllegalArgumentException(
                    "Invalid Arguemnt: Start Time or End Time must be after 2000-1-1-00-00"
                    + "and before 2100-12-31-23-59");
        }
        if (this.startTime.after(this.endTime)) {
            throw new IllegalArgumentException("Invalid Arguemnt: Start Time is after End Time");
        }
        if (eventName == null || eventName.length() < 1) {
            throw new IllegalArgumentException(
                    "Invalid Arguemnt: Must give event a valid short name");
        }
        
        // set first day of the week
        this.startTime.setFirstDayOfWeek(Calendar.MONDAY);
        this.endTime.setFirstDayOfWeek(Calendar.MONDAY);
        
        // assign variable
        this.eventName = eventName;
        this.description = "";
        this.attendeeIDs = listAttendees;
        
    }
    
    /**
     * helper function to determine if a time is within the valid range defined by this application
     * @param time
     * @return
     */
    private static boolean isValidTime(Calendar time) {
        if (time == null) {
            return false;
        }
        return time.after(ICalendarApp.MINCAL) && time.before(ICalendarApp.MAXCAL);
    }
    
    /**
     * Helper function to parse the input string date to Calendar object
     * @param time
     * @return
     */
    protected static Calendar parseTime(String time) {
        String[] working = time.split("-");
        
        // check input length
        if (working.length != 5) {
            return null;
        }
        
        int year;
        int month;
        int day;
        int hour;
        int min;
        
        // check input format
        try {
            year = Integer.parseInt(working[0]);
            month = Integer.parseInt(working[1]);
            day = Integer.parseInt(working[2]);
            hour = Integer.parseInt(working[3]);
            min = Integer.parseInt(working[4]);
        } catch (NumberFormatException e) {
            return null;
        }
        
        // check input range
        if (year < 2000 || year > 2100) {
            return null;
        } else if (month < 1 || month > 12) {
            return null;
        } else if (day < 1 || day > 31) {
            return null;
        } else if (hour < 0 || hour > 23) {
            return null;
        } else if (min < 0 || min > 59) {
            return null;
        }
        
        Calendar ret = new GregorianCalendar(year, month - 1, day, hour, min);
        // important! month in Calendar is actual month - 1 
        return ret; 
    }
    
    
    @Override
    public void editEventName(String name) {
        this.eventName = name;
    }

    @Override
    public void editDescription(String description) {
        this.description = description;
    }

    @Override
    public List<Integer> getAttendee() {
        List<Integer> list = new LinkedList<Integer>();
        
        // return a list of NON-duplicates
        for (int i = 0; i < attendeeIDs.size(); i++) {
            if (!list.contains(attendeeIDs.get(i))) {
                list.add((int) attendeeIDs.get(i));
            }
        }
        return list;
    }

    @Override
    public boolean changeStartTime(String sTime) {
        Calendar time = parseTime(sTime);
        
        if (time == null) {
            return false;
        }
        
        // validate time
        if (time.after(this.endTime) || !isValidTime(time)) {
            return false;
        }
        this.startTime = time;
        return true;
    }


    @Override
    public boolean changeEndTime(String eTime) {
        
        Calendar time = parseTime(eTime);
        
        if (time == null) {
            return false;
        }
        
        // validate time
        if (time.before(this.startTime) || !isValidTime(time)) {
            return false;
        }
        this.endTime = time;
        return true;
    }

    @Override
    public boolean changeDuration(int hours, int mins) {
        // validate input
        if (hours < 0 || mins < 0 || (hours == 0 && mins == 0)) {
            return false;
        }
        
        // make a copy of input 
        Calendar newEnd = (Calendar) this.startTime.clone();
        newEnd.add(Calendar.HOUR_OF_DAY, hours);
        newEnd.add(Calendar.MINUTE, mins);
        this.endTime = newEnd;
        return true;
    }

    @Override
    public int compareTo(IEvent o) {
        // compare by start time
        Calendar other = o.getStartTime();
        // compare end time if start times are the same
        if (this.startTime.compareTo(other) == 0) {
            Calendar otherE = o.getEndTime();
            return this.endTime.compareTo(otherE);
        }
        return this.startTime.compareTo(other);
    }

    @Override
    public Calendar getStartTime() {
        // return HARD-COPY
        return (Calendar) this.startTime.clone();
    }

    @Override
    public Calendar getEndTime() {
        // return HARD-COPY
        return (Calendar) this.endTime.clone();
    }

    @Override
    public String getEventName() {
        return this.eventName;
    }

}
