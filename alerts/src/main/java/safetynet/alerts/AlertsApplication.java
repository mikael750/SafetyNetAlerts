package safetynet.alerts;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import safetynet.alerts.controller.SystemController;

import java.io.IOException;

@SpringBootApplication
public class AlertsApplication {

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();
		SystemController.initDataBase();
		SpringApplication.run(AlertsApplication.class, args);
	}

}
