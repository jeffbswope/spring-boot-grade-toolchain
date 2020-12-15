package org.github.jeffbswope.springbootgradletoolchain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootGradleToolchainApplication {
	private static final Logger log = LoggerFactory.getLogger(SpringBootGradleToolchainApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootGradleToolchainApplication.class, args);
	}

	@Bean
	public CommandLineRunner logNotice() {
		return args -> {
			log.info("""
					Multi-line string confirms we are using the newer java here than the one running Gradle.
					""");
		};
	}
}
