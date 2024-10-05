package org.recap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * The type SCSB Common Application.
 */
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class ScsbSamlApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ScsbSamlApplication.class, args);
	}
}
