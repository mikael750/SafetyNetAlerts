package safetynet.alerts.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.controller.FireStationsController;
import safetynet.alerts.controller.SystemController;
import safetynet.alerts.model.FireStations;
import safetynet.alerts.service.FireStationsDaoImpl;
import safetynet.alerts.service.MedicalRecordsDaoImpl;
import safetynet.alerts.service.PersonsDaoImpl;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FireStationsIT {

    @Autowired
    FireStationsController fireStationsController;

    @BeforeAll
    private static void setUp() throws IOException {
        SystemController.initDataBase();
        PersonsDaoImpl.load();
        FireStationsDaoImpl.load();
        MedicalRecordsDaoImpl.load();
    }

    @Test
    public void fireStationsController_ShouldAddNewStation(){
        var test = new FireStations("1stHouse","1");
        fireStationsController.addFireStations(test);
        assertTrue(Objects.requireNonNull(fireStationsController.getFireStations().getBody()).contains(test));
    }

    @Test
    public void fireStationsController_ShouldUpdateStation(){
        var test = new FireStations("2ndHouse","2");
        fireStationsController.addFireStations(test);
        FireStations fireStationsDetail = new FireStations("2ndHouse","3");
        try {
            fireStationsController.updateFireStations("2ndHouse",fireStationsDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("3", fireStationsController.showFireStations("2ndHouse").getStation());
    }

    @Test
    public void fireStationsController_ShouldDeleteStation() {
        var test = new FireStations("1stHouse","1");
        fireStationsController.deleteAddress("1stHouse");
        assertFalse(Objects.requireNonNull(fireStationsController.getFireStations().getBody()).contains(test));
    }

    @AfterAll
    static void cleanDataBase() throws IOException {
        SystemController.initDataBase();
    }
}
