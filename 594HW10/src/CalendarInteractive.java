import java.util.Calendar;
import java.util.InputMismatchException;
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
            
            System.out.printf(
                    "ID of the operation:\n" + "1 - User log-in\n" + "2 - find common time\n" + "3 - show all users\n");
            try {
                int id = s.nextInt();
                s.nextLine();
                switch (id) {
                    case 1:
                        System.out.println("Please Enter your name:");
                        user(s.nextLine());
                        break;
                        
                    case 2:
                        // TODO: implement this
                        break;
                        
                    case 3:
                        if (app.getUsers().size()==0) {
                            System.out.println("Zero User!!");
                        } else {
                            System.out.println("Available User:");
                            for (IUser u : app.getUsers()) {
                                System.out.println(u.getUserName());
                            }
                        }
                        break;
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.printf("Invalid input, try again.\n\n");
                s.nextLine();
                continue;
            }

            System.out.println();
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
            s.nextLine();
            switch (id) {
                case 1:
               
                    String eventName;
                    String startTime;
                    String endTime;
                    String attendee;
                    while (true) {
                        System.out.println("Enter event name:");
                        eventName = s.nextLine();
                        System.out.println("Enter start time (format YYYY-MM-DD-HH-MM):");
                        startTime = s.nextLine();
                        System.out.println("Enter end time (format YYYY-MM-DD-HH-MM):");
                        endTime = s.nextLine();
                        System.out.println("Enter attendee names (format name-name...):");
                        attendee = s.nextLine();
                        // TODO: attendee
                        if (!currUser.addEvent(startTime, endTime, eventName, app.parseAttendee(attendee))) {
                            System.out.printf(
                                    "Invalid input. Please enter a correctly formated time period between year 2000 and 2100\n\n",
                                    eventName);
                            continue;
                        } else {
                            break;
                        } 
                    }
                    System.out.printf("Event \"%s\" added!\n", eventName);
                    break;
                case 2:
                    System.out.println("Enter event name:");
                    String target = s.nextLine();
    
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
                    String search = s.nextLine();
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
                    String date = s.nextLine() + "-1-0";
                    Calendar day = Event.parseTime(date);
                    while (day == null) {
                        System.out.printf("Please enter a valid day between year 2000 and 2100, in YYYY-MM-DD format\n");
                        date = s.nextLine() + "-1-0";
                        day = Event.parseTime(date);
                    }
                    currUser.viewCalendarByDay(day);
                    break;
                case 6:
                    System.out.println("Enter a date of the week you want to view: (format: YYYY-MM-DD)");
                    String dayOfWeek = s.nextLine() + "-1-0";
                    Calendar cal = Event.parseTime(dayOfWeek);
                    while (cal == null) {
                        System.out.printf("Please enter a valid day between year 2000 and 2100, in YYYY-MM-DD format\n");
                        dayOfWeek = s.nextLine() + "-1-0";
                        cal = Event.parseTime(dayOfWeek);
                    }
                    currUser.viewCalendarByWeek(cal);
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
