package safetynet.alerts;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlertsApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		safetynet.alerts.DAO.PersonsDaoImpl.load();
		safetynet.alerts.DAO.FireStationsDaoImpl.load();
		safetynet.alerts.DAO.MedicalRecordsDaoImpl.load();
		SpringApplication.run(AlertsApplication.class, args);
	}

}
