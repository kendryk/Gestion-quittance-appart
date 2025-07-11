package fr.quittance.common.utils;

public class DateUtils {

    public static boolean isToday(java.time.LocalDate date) {
        return java.time.LocalDate.now().equals(date);
    }
}
