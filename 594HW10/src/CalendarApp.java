import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CalendarApp implements ICalendarApp {

    private List<IUser> users;
    private int nextAvailID;

    public CalendarApp() {
        // constructor initialize calendar
        calendarInit();
    }

    @Override
    public void calendarInit() {
        // set all fields to their default values
        this.users = new ArrayList<IUser>();
        this.nextAvailID = 1;
    }

    @Override
    public IUser addUser(String name) {
        // creat a user with the provided name and assign ID
        IUser user = new User(name, this.nextAvailID);

        // add the user into the calendar app
        users.add(user);

        // update next available ID
        this.nextAvailID++;
        return user;
    }

    @Override
    public boolean deleteUser(int id) {
        // loop to check if there is a user with the target ID
        for (IUser user : users) {
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
        // initialize an arraylist
        List<Calendar[]> list = new ArrayList<>();

        // if either input is empty, return an empty list
        if (c1.isEmpty() || c2.isEmpty()) {
            return list;
        }

        int size2 = c2.size();
        // loop through calendar 1 slots
        for (int i = 0; i < c1.size(); i++) {
            // get start and end time for index i slot in calendar 1
            Calendar start = c1.get(i)[0];
            Calendar end = c1.get(i)[1];

            // find calendar 2 slot with end time after slot i
            int index = 0;
            while (index < size2 && c2.get(index)[1].before(start)) {
                index++;
            }

            // if index reaches end of calendar 2, skip the loop
            if (index >= size2) {
                continue;
            }

            // if c2 event starts earlier, and c2 event ends earlier
            // add slot with c1 start time and c2 end time
            if (c2.get(index)[0].before(start) && c2.get(index)[1].before(end)) {
                Calendar[] tuple = { start, c2.get(index)[1] };
                list.add(tuple);
                index++;
                // if c2 event starts earlier, and c2 event end later
                // use c1 event start and end time
            } else if (c2.get(index)[0].before(start)
                    && (c2.get(index)[1].after(end) || c2.get(index)[1].equals(end))) {
                Calendar[] tuple = { start, end };
                list.add(tuple);
                continue;
            }

            // if index reaches end of calendar 2, skip the loop
            if (index >= size2) {
                continue;
            }

            // add subsequent events that have start time before
            // c1 event end time to the overlap list
            while (index < size2 && c2.get(index)[0].before(end)) {
                if (c2.get(index)[1].after(end)) {
                    Calendar[] tuple = { c2.get(index)[0], end };
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
    public List<Calendar[]> findCommonMeetingTime(Calendar startTime, Calendar endTime) {
        // if list of users is empty, return null
        if (this.users.isEmpty()) {
            return null;
        }

        // for the first user, acquire available time slots
        // add these time slots to default list
        List<Calendar[]> fullList = this.users.get(0).getAllFreeTime(startTime, endTime);
        List<Calendar[]> list = this.users.get(0).getAvailableTime(fullList);

        // for the rest of the users, acquire available time slots
        // and compare them with the default list
        // if there are overlaps, delete the ones that are not overlapping
        // and edit the ones that are partially overlapping
        for (int i = 1; i < this.users.size(); i++) {
            List<Calendar[]> fullList2 = this.users.get(i).getAllFreeTime(startTime, endTime);
            List<Calendar[]> list2 = this.users.get(i).getAvailableTime(fullList2);

            list = this.findOverlap(list, list2);
            if (list.isEmpty()) {
                break;
            }
        }

        // delete events with the same start and end time
        // user iterator.remove() to avoid concurrent modification exception
        Iterator<Calendar[]> iter = list.iterator();
        while (iter.hasNext()) {
            Calendar[] item = iter.next();
            if (item[0].equals(item[1])) {
                iter.remove();
            }
        }
        return list;
    }

    @Override
    public int getNextAvailID() {
        return this.nextAvailID;
    }

    @Override
    public List<IUser> getUsers() {
        return this.users;
    }

    @Override
    public List<IUser> parseAttendee(String input) {
        List<IUser> res = new LinkedList<>();

        String[] split = input.split("-");

        if (split.length > 0) {
            for (String name : split) {
                boolean contain = false;

                // search for user
                for (IUser u : this.users) {
                    if (u.getUserName().equals(name)) {
                        res.add(u);
                        contain = true;
                    }
                }

                // add user to calendar if not contained
                if (!contain) {
                    IUser add = this.addUser(name);
                    res.add(add);
                }
            }
        }
        return res;
    }

}
