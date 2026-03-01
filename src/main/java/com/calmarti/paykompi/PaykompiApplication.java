package com.calmarti.paykompi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PaykompiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaykompiApplication.class, args);
	}

}
