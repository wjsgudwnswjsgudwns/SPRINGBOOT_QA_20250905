package com.jhj.qa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class JhjProjectApplication extends SpringBootServletInitializer{

	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(JhjProjectApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(JhjProjectApplication.class, args);
	}

}
