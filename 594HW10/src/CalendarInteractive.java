import java.util.Calendar;
import java.util.LinkedList;
import java.util.Scanner;

public class CalendarInteractive {

    private CalendarApp app;
    private Scanner s;
    private IUser currUser;

    public void init() {
        this.app = new CalendarApp();
        this.s = new Scanner(System.in);
    }

    public void prompt() {
        while (true) {
            System.out.println("Welcome to Calendar!");
            System.out.println("Please Enter your name:");
            user(s.next());
        }
    }

    private void user(String name) {

        boolean contain = false;
        for (int i = 0; i < app.getUsers().size(); i++) {
            if (app.getUsers().get(i).getUserName().equals(name)) {
                contain = true;
                currUser = app.getUsers().get(i);
            }
        }

        if (!contain) {
            currUser = app.addUser(name);
        }

        while (true) {
            System.out.printf(
                    "ID of the operation:\n" + "1 - create event\n" + "2 - delete event\n" + "3 - view next event\n"
                            + "4 - search event\n" + "5 - day view\n" + "6 - weekly view\n" + "7 - switch user\n");

            int id = s.nextInt();
            System.out.printf("\n");
            switch (id) {
            case 1:
                // TODO: input with spaces

                System.out.println("Enter event name:");
                String eventName = s.next();
                System.out.println("Enter start time (format YYYY-MM-DD-HH-MM):");
                String startTime = s.next();
                System.out.println("Enter end time (format YYYY-MM-DD-HH-MM):");
                String endTime = s.next();
                System.out.println("Enter attendee names (format name-name...):");
                String attendee = s.next();

                // TODO: attendee
                while (!currUser.addEvent(startTime, endTime, eventName, new LinkedList<Integer>())) {
                    System.out.printf(
                            "Invalid input. Please enter a correctly formated time period between year 2000 and 2100\n",
                            eventName);
                }
                System.out.printf("Event \"%s\" added!\n", eventName);
                break;
            case 2:
                System.out.println("Enter event name:");
                String target = s.next();

                boolean ret = currUser.deleteEvent(target);
                if (ret) {
                    System.out.printf("Event <%s> deleted!\n", target);
                } else {
                    System.out.printf("No such event.\n");
                }

                break;
            case 3:
                IEvent next = currUser.nextEvent();
                if (next != null) {
                    System.out.printf("The upcoming event for user %s:\n", currUser.getUserName());
                    System.out.printf("%tB %<te, %<tY -- %tR - %tR %s\n", next.getStartTime(), next.getStartTime(),
                            next.getEndTime(), next.getEventName());
                } else {
                    System.out.printf("No upcoming event for user %s:\n", currUser.getUserName());
                }
                break;
            case 4:
                System.out.println("Enter event name:");
                String search = s.next();
                IEvent result = currUser.searchEvent(search);
                if (result != null) {
                    System.out.printf("Event found:\n");
                    System.out.printf("%tB %<te, %<tY -- %tR - %tR %s\n", result.getStartTime(), result.getStartTime(),
                            result.getEndTime(), result.getEventName());
                } else {
                    System.out.printf("No such event exist %s!\n");
                }
                break;
            case 5:
                System.out.println("Enter date: (format: YYYY-MM-DD)");
                String date = s.next() + "-1-0";
                Calendar day = Event.parseTime(date);
                if (day == null) {
                    System.out.printf("Please enter a valid day between year 2000 and 2100\n");
                } else {
                    currUser.viewCalendarByDay(day);
                }
                break;
            case 6:
                System.out.println("Enter a date of the week you want to view: (format: YYYY-MM-DD)");
                String dayOfWeek = s.next() + "-1-0";
                Calendar cal = Event.parseTime(dayOfWeek);
                if (cal == null) {
                    System.out.printf("Please enter a valid day between year 2000 and 2100\n");
                } else {
                    currUser.viewCalendarByWeek(cal);
                }
                break;

            case 7:
                return;
            default:
                break;
            }

            System.out.printf("\n");
        }
    }

    public static void main(String[] args) {
        CalendarInteractive ci = new CalendarInteractive();
        ci.init();
        ci.prompt();
    }
}
