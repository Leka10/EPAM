package utils;

public class DateTimeUtils extends LoggerUtils {

    public static void wait(int second) {
        try {
            Thread.sleep(1000L * second);
        } catch (InterruptedException e) {
            log.warn("InterruptedException in Thread.sleep(). Message: " + e.getMessage());
        }
    }
}
