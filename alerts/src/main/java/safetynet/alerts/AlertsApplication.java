package safetynet.alerts;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import safetynet.alerts.util.AlertsUtils;
import safetynet.alerts.service.FireStationsDaoImpl;
import safetynet.alerts.service.MedicalRecordsDaoImpl;
import safetynet.alerts.service.PersonsDaoImpl;

import java.io.IOException;

@SpringBootApplication
public class AlertsApplication {

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();
		AlertsUtils.initDataBase();
		PersonsDaoImpl.load();
		FireStationsDaoImpl.load();
		MedicalRecordsDaoImpl.load();
		SpringApplication.run(AlertsApplication.class, args);
	}

}
