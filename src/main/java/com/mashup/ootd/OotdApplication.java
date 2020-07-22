package com.mashup.ootd;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class OotdApplication {
	
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:aws.yml";
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(OotdApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
