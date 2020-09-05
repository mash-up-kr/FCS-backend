package com.mashup.ootd.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class DateUtils {

	private static final DateTimeFormatter STR_FMT = DateTimeFormatter.ofPattern("yy년 M월 dd일 E요일");

	public static String toDateStr(LocalDate localDate) {
		return Optional.ofNullable(localDate)
				.map(date -> date.format(STR_FMT))
				.orElse("");
	}

	public static String toDateStr(LocalDateTime localDateTime) {
		return Optional.ofNullable(localDateTime)
				.map(date -> date.format(STR_FMT))
				.orElse("");
	}

}
