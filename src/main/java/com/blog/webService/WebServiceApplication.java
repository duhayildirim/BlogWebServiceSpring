package com.blog.webService;

import com.blog.webService.user.User;
import com.blog.webService.user.UserRepository;
import com.blog.webService.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner createInitialUsers(UserService userService) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				User user = new User();
				user.setUsername("duha");
				user.setEmail("duha@gmail.com");
				user.setPassword("Duha123.");

				userService.save(user);
			}
		};
	}

}
