package calendar;

import java.util.Calendar;

/**
 * @author laiw
 * @date 2023/5/31 15:26
 */
public class Test {
    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 10);
        System.out.println(c.getTime());

        c.set(Calendar.HOUR_OF_DAY, 10);
        System.out.println(c.getTime());

        String s = null;
        System.out.println(s instanceof String);
        Boolean bo = null;
        System.out.println(Boolean.TRUE.equals(bo));
    }
}
