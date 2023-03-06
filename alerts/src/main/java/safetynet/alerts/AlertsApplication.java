package safetynet.alerts;

import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//@ComponentScan("safetynet.alerts.DAO.FireStationsDao")
@SpringBootApplication
public class AlertsApplication {

	//public static byte[] data = new byte[0];
	public static void main(String[] args) throws IOException {
		//data = Files.readAllBytes(new File("../resources/data.json").toPath());
		BasicConfigurator.configure();
		safetynet.alerts.DAO.PersonsDaoImpl.load();
		safetynet.alerts.DAO.FireStationsDaoImpl.load();
		safetynet.alerts.DAO.MedicalRecordsDaoImpl.load();
		SpringApplication.run(AlertsApplication.class, args);
	}

}
