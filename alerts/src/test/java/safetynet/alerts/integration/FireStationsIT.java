package safetynet.alerts.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.controller.FireStationsController;
import safetynet.alerts.model.FireStations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FireStationsIT {

    @Autowired
    private FireStationsDao fireStationDao;

    FireStationsController fireStationsController;
    static FireStations test1;
    static FireStations test2;
    static String test3;

    @BeforeAll
    private static void setUp() throws IOException {
        FireStationsController.getDataBase();
        test1 = new FireStations("1stHouse","1");
        test2 = new FireStations("2ndHouse","2");
        test3 = "ShouldReturnFalse";

    }

    @AfterAll
    static void cleanDataBase() throws IOException {
        FireStationsController.getDataBase();
    }

    @BeforeEach
    private void setUpPerTest() {
        fireStationsController = new FireStationsController(fireStationDao);
    }

    @Test
    public void fireStationsController_ShouldAddNewStation(){
        fireStationsController.addFireStations(test1);
        assertTrue(fireStationsController.getFireStations().contains(test1));
    }

    @Test
    public void fireStationsController_ShouldNotAddNewStation(){
        fireStationsController.addFireStations(null);
        assertFalse(fireStationsController.getFireStations().contains(test3));
    }

    @Test
    public void fireStationsController_ShouldUpdateStation(){
        fireStationsController.addFireStations(test2);
        FireStations fireStationsDetail = new FireStations("2ndHouse","3");
        try {
            fireStationsController.updateFireStations("2ndHouse",fireStationsDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertSame("3", fireStationsController.showFireStations("2ndHouse").getStation());
    }

    @Test
    public void fireStationsController_ShouldDeleteStation() {
        fireStationsController_ShouldAddNewStation();
        fireStationsController.deleteAddress("1stHouse");
        assertFalse(fireStationsController.getFireStations().contains(test1));
    }

}
