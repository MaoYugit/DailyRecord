package learn.maoyu.api.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class DateTimePractice {
    public static void main(String[] args) {
        // 1. 获取当前日期和时间
        LocalDateTime now = LocalDateTime.now();
        LocalTime localTime = LocalTime.now();
        System.out.println("当前完整时间: " + now);
        System.out.println("当前日期: " + now.toLocalDate());
        System.out.println("当前时间: " + now.toLocalTime());
        System.out.println("当前时间: " + localTime);

        // 2. 创建指定的日期和时间
        LocalDateTime specificDateTime = LocalDateTime.of(2024, 10, 1, 8, 0, 0);
        System.out.println("国庆节早上8点: " + specificDateTime);

        // 3. 格式化：将日期时间对象转为漂亮的字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String formattedNow = now.format(formatter);
        System.out.println("格式化后的当前时间: " + formattedNow);

        // 4. 解析：将字符串转为日期时间对象
        String timeStr = "2025-01-01 00:00:00";
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime newYear = LocalDateTime.parse(timeStr, parser);
        System.out.println("解析出的新年时间: " + newYear);

        // 5. 获取日期时间的各个部分
        System.out.println("今年是: " + now.getYear() + "年");
        System.out.println("今天是今年的第 " + now.getDayOfYear() + " 天");

        // 6. 日期时间的计算
        LocalDateTime tomorrow = now.plusDays(1);
        System.out.println("明天这个时间: " + tomorrow);
        LocalDateTime lastMonth = now.minusMonths(1);
        System.out.println("上个月这个时间: " + lastMonth);

        // 7. 计算两个日期之间的间隔
        LocalDate today = LocalDate.now();
        LocalDate myBirthday = LocalDate.of(2024, 12, 25);
        long daysUntilBirthday = ChronoUnit.DAYS.between(today, myBirthday);
        System.out.println("距离我的生日还有: " + daysUntilBirthday + " 天");
    }
}