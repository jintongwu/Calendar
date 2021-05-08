import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
public class Test {

    public static void main(String[] args) {
        
        Calendar cal = Calendar.getInstance();
        
        // first get
        int year = cal.get(Calendar.YEAR);
        System.out.println(year);
        int month = cal.get(Calendar.MONTH) + 1;
        System.out.println(month);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        System.out.println(day);
        int hour = cal.get(Calendar.HOUR);
        System.out.println(hour);
        int minute = cal.get(Calendar.MINUTE);
        System.out.println(minute);
        

//        // add one year
//        Calendar cal2 = cal;
//        cal2.add(Calendar.YEAR, 1);
//        
//        try {
//            Thread.sleep(120000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        
//        cal = Calendar.getInstance();
//        
//        
//        // cal should have 2 more mins now
//        year = cal.get(Calendar.YEAR);
//        System.out.println(year);
//        month = cal.get(Calendar.MONTH) + 1;
//        System.out.println(month);
//        day = cal.get(Calendar.DAY_OF_MONTH);
//        System.out.println(day);
//        hour = cal.get(Calendar.HOUR);
//        System.out.println(hour);
//        minute = cal.get(Calendar.MINUTE);
//        System.out.println(minute);
//        
//        
//        // cal2 two should have 2 min less than cal
//        year = cal2.get(Calendar.YEAR);
//        System.out.println(year);
//        month = cal2.get(Calendar.MONTH) + 1;
//        System.out.println(month);
//        day = cal2.get(Calendar.DAY_OF_MONTH);
//        System.out.println(day);
//        hour = cal2.get(Calendar.HOUR);
//        System.out.println(hour);
//        minute = cal2.get(Calendar.MINUTE);
//        System.out.println(minute);
        
        
        // customized Calendar object
        Calendar cal3 = Calendar.getInstance();
        cal3.set(1997, 4, 4, 7, 12); /// MONTH - 1 ?? 
        year = cal3.get(Calendar.YEAR);
        System.out.println(year);
        month = cal3.get(Calendar.MONTH) + 1;
        System.out.println(month);
        day = cal3.get(Calendar.DAY_OF_MONTH);
        System.out.println(day);
        hour = cal3.get(Calendar.HOUR);
        System.out.println(hour);
        minute = cal3.get(Calendar.MINUTE);
        System.out.println(minute);
        
        
        String test = "2021-5-1-10-22";
        String[] split = test.split("-");
        
        System.out.println("SPLIT");
//        for (String s : split) {
//            System.out.println(s);
//        }

        
        cal3 = new GregorianCalendar(1997, 0, 4, 23, 0);
        Calendar cal4 = new GregorianCalendar(1997, 0, 4, 11, 0);
        
        System.out.println(cal3.before(cal4));
        System.out.println(cal3.after(cal4));
        year = cal3.get(Calendar.YEAR);
        System.out.println(year);
        month = cal3.get(Calendar.MONTH) + 1;
        System.out.println(month);
        day = cal3.get(Calendar.DAY_OF_MONTH);
        System.out.println(day);
        hour = cal3.get(Calendar.HOUR);
        System.out.println(hour);
        minute = cal3.get(Calendar.MINUTE);
        System.out.println(minute);
        
        

        System.out.printf("%tR\n", cal3);
        System.out.println();
        
        
        
        
        
        
        String[][] view = ICalendarApp.WEEKVIEW.clone();
        
        String etc = "...";
        view[1][1] = "Buy Carrots at Wholefoods";
        view[5][5] = "594 HW due";
        if (view[1][1].length() > 14) {
            view[1][1] = view[1][1].substring(0, 14) + etc;
        }
        
        
        int days = cal3.get(Calendar.DAY_OF_MONTH);
        
        System.out.println(ICalendarApp.LINESEPARATE);
        System.out.printf("%60sWeek of %tB %<te, %<tY \n", " ", cal3);
        System.out.printf("%s\n%5s | %s - %-12d| %s - %-12d| %s - %-12d| %s - %-12d| %s - %-12d| %s - %-12d| %s - %-12d|\n", ICalendarApp.LINESEPARATE,
                view[0][0], view[0][1], days, view[0][2], days+1, view[0][3], days+2, 
                view[0][4], days+3, view[0][5], days+4, view[0][6], days+5, view[0][7], days+6);
        for (int i = 1; i < 14; i ++) {
            System.out.printf("%s\n%5s | %-18s| %-18s| %-18s| %-18s| %-18s| %-18s| %-18s|\n", ICalendarApp.LINESEPARATE,
                    view[i][0], view[i][1], view[i][2], view[i][3],
                    view[i][4], view[i][5], view[i][6], view[i][7]);
        }
        System.out.println(ICalendarApp.LINESEPARATE);
        
    }

}
