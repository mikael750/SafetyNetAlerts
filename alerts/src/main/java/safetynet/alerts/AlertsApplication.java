package safetynet.alerts;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import safetynet.alerts.service.FireStationsDaoImpl;
import safetynet.alerts.service.MedicalRecordsDaoImpl;
import safetynet.alerts.service.PersonsDaoImpl;

@SpringBootApplication
public class AlertsApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		PersonsDaoImpl.load();
		FireStationsDaoImpl.load();
		MedicalRecordsDaoImpl.load();
		SpringApplication.run(AlertsApplication.class, args);
	}

}
