package safetynet.alerts.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.controller.FireStationsController;
import safetynet.alerts.model.FireStations;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FireStationsIT {

    @Autowired
    private FireStationsDao fireStationDao;

    FireStationsController fireStationsController;
    static FireStations test1;
    static FireStations test2;

    @BeforeAll
    private static void setUp() throws Exception {
        test1 = new FireStations("1stHouse","1");
        test2 = new FireStations("2ndHouse","2");

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
