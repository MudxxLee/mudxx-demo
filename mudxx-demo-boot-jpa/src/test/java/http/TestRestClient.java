package http;


import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author laiw
 * @date 2023/9/21 12:00
 */
public class TestRestClient {

    public static void main(String[] args) {

        long milli = LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0)
                .atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
        System.out.println(milli);
    }

}
