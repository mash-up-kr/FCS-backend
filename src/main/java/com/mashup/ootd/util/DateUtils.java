package com.mashup.ootd.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	private static final DateTimeFormatter STR_FMT = DateTimeFormatter.ofPattern("yy년 M월 dd일 E요일");

	public static String toDateStr(LocalDateTime localDateTime) {
		return localDateTime.format(STR_FMT);
	}

}
