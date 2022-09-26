package academy.mindswap.Mindera_Events;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@Slf4j
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MinderaEventsApplication {

	private final static Logger LOGGER = LoggerFactory.
			getLogger(MinderaEventsApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting App");
		SpringApplication.run(MinderaEventsApplication.class, args);
	}

}
