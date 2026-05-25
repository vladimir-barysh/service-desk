package ru.altaiensb.service_desk.util;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class WorkingHoursUtil {

    private static final ZoneId ZONE = ZoneId.of("Asia/Barnaul");
    private static final LocalTime WORK_START = LocalTime.of(8, 30);
    private static final LocalTime WORK_END_MON_THU = LocalTime.of(17, 30);
    private static final LocalTime WORK_END_FRI = LocalTime.of(16, 30);
    private static final LocalTime LUNCH_START = LocalTime.of(12, 30);
    private static final LocalTime LUNCH_END = LocalTime.of(13, 18);

    public static Instant addWorkHours(Instant startInstant, int hours) {
        ZonedDateTime start = startInstant.atZone(ZONE);
        ZonedDateTime current = start;
        int remainingMinutes = hours * 60;

        while (remainingMinutes > 0) {
            // Нормализуем текущее время: если попало на выходной или вне рабочего времени, переводим к ближайшему рабочему слоту
            current = normalizeToWorkingTime(current);
            ZonedDateTime endOfWorkDay = getEndOfWorkDay(current);
            long minutesUntilEnd = ChronoUnit.MINUTES.between(current, endOfWorkDay);
            // Если до конца дня достаточно минут, прибавляем и выходим
            if (minutesUntilEnd >= remainingMinutes) {
                current = current.plusMinutes(remainingMinutes);
                remainingMinutes = 0;
            } else {
                remainingMinutes -= minutesUntilEnd;
                // Переходим на начало следующего рабочего дня (следующий день, 8:30)
                current = current.plusDays(1).with(WORK_START);
            }
        }
        return current.toInstant();
    }

    private static ZonedDateTime normalizeToWorkingTime(ZonedDateTime dt) {
        // Выходные -> следующий понедельник 8:30
        while (dt.getDayOfWeek() == DayOfWeek.SATURDAY || dt.getDayOfWeek() == DayOfWeek.SUNDAY) {
            dt = dt.plusDays(1).with(WORK_START);
        }
        LocalTime time = dt.toLocalTime();
        // Если раньше начала дня -> начало дня
        if (time.isBefore(WORK_START)) {
            dt = dt.with(WORK_START);
        }
        // Если обед -> после обеда
        if (!time.isBefore(LUNCH_START) && time.isBefore(LUNCH_END)) {
            dt = dt.with(LUNCH_END);
        }
        // Если после окончания рабочего дня -> следующий рабочий день 8:30
        LocalTime endWork = (dt.getDayOfWeek() == DayOfWeek.FRIDAY) ? WORK_END_FRI : WORK_END_MON_THU;
        if (time.isAfter(endWork)) {
            dt = dt.plusDays(1).with(WORK_START);
            return normalizeToWorkingTime(dt);
        }
        return dt;
    }

    private static ZonedDateTime getEndOfWorkDay(ZonedDateTime dt) {
        LocalTime endWork = (dt.getDayOfWeek() == DayOfWeek.FRIDAY) ? WORK_END_FRI : WORK_END_MON_THU;
        return dt.with(endWork);
    }
}