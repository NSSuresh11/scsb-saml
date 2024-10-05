package org.recap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * The type SCSB Common Application.
 */
@SpringBootApplication
@PropertySource("classpath:application.properties")
public class ScsbSamlApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ScsbCommonApplication.class, args);
	}
}
