package com.profsoft.smsnotifications.model.commonutils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

/**
 *
 * @author Maroo
 */
public class TimeUtils {
	public static boolean isHoliday(LocalDateTime day) {
		if (day.getDayOfWeek() == DayOfWeek.SUNDAY) {
			return true; //Niedziela
		}
		if (day.getDayOfWeek() == DayOfWeek.SATURDAY) {
			return true; //Sobota
		}
		if (day.getMonth() == Month.JANUARY && day.getDayOfMonth() == 1) {
			return true; // Nowy Rok
		}
		if (day.getMonth() == Month.MAY && day.getDayOfMonth() == 1) {
			return true; // 1 maja
		}
		if (day.getMonth() == Month.MAY && day.getDayOfMonth() == 3) {
			return true; // 3 maja
		}
		if (day.getMonth() == Month.AUGUST && day.getDayOfMonth() == 15) {
			return true; // Wniebowzięcie Najświętszej Marii Panny, Święto Wojska Polskiego
		}
		if (day.getMonth() == Month.NOVEMBER && day.getDayOfMonth() == 1) {
			return true; // Dzień Wszystkich Świętych
		}
		if (day.getMonth() == Month.NOVEMBER && day.getDayOfMonth() == 11) {
			return true; // Dzień Niepodległości
		}
		if (day.getMonth() == Month.DECEMBER && day.getDayOfMonth() == 25) {
			return true; // Boże Narodzenie 
		}
		if (day.getMonth() == Month.DECEMBER && day.getDayOfMonth() == 26) {
			return true; // Boże Narodzenie
		}
		int a = day.getYear() % 19;
		int b = day.getYear() % 4;
		int c = day.getYear() % 7;
		int d = (a * 19 + 24) % 30;
		int e = (2 * b + 4 * c + 6 * d + 5) % 7;
		if (d == 29 && e == 6) {
			d -= 7;
		}
		if (d == 28 && e == 6 && a > 10) {
			d -= 7;
		}
		LocalDateTime easter = LocalDateTime.of(day.getYear(), 3, 22, 0, 0).plusDays(d + e);
		if (day.plusDays(-1) == easter) {
			return true; // Wielkanoc (poniedziałek) 
		}
		if (day.plusDays(-60) == easter) {
			return true; // Boże Ciało return false;
		}
		return false;
	}
}
