import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimePractice {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前完整时间: " + now);
        System.out.println("当前日期: " + now.toLocalDate());
        System.out.println("当前时间: " + now.toLocalTime());

        LocalDateTime specificDateTime = LocalDateTime.of(2025, 10, 1, 8, 0,0);
        System.out.println("国庆节早上8点: " + specificDateTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        System.out.println("格式化后的当前时间: " + formattedNow);

        String timeStr = "2025-01-01 00:00:00";
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime newYear = LocalDateTime.parse(timeStr, parser);
        System.out.println("解析出的新年时间: " + newYear);
    }
}