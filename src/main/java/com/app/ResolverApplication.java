package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.resolver.resolvers.Resolver;

@SpringBootApplication
@Controller
public class ResolverApplication {

	public static void main(String[] args) {
		Resolver.setModulesDirectory(System.getProperty("user.dir")+"/target/classes/web/node_modules"); // temporary solution
		Resolver.setPrefix("");
		SpringApplication.run(ResolverApplication.class, args);
	}

	@GetMapping("/")
	public static String req() {
		return "index.html";
	}
}
