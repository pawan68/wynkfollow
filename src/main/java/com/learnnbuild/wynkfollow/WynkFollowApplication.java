package com.learnnbuild.wynkfollow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class WynkFollowApplication {

	public static void main(String[] args) {
		SpringApplication.run(WynkFollowApplication.class, args);
	}

}
