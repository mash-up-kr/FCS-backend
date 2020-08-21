package com.mashup.ootd.util;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DateUtilsTest {

	@Test
	void test_toDateStr() {
		LocalDateTime localDateTime = LocalDateTime.of(2020, 8, 16, 12, 0, 0);

		String dateStr = DateUtils.toDateStr(localDateTime);

		Assertions.assertThat(dateStr).isEqualTo("20년 8월 16일 일요일");
	}

}
