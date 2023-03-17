package safetynet.alerts.unitaire;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.DAO.FireStationsDaoImpl;
import safetynet.alerts.DAO.MedicalRecordsDaoImpl;
import safetynet.alerts.model.FireStations;
import safetynet.alerts.model.MedicalRecords;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FireStationsDAOTest {

    private static FireStationsDaoImpl fireStationsDaoImpl;

    @Mock
    private static FireStations fireStations;

    @BeforeAll
    private static void setUp() throws Exception {
        FireStationsDaoImpl.load();
    }

    @BeforeEach
    private void setUpPerTest() {
        fireStationsDaoImpl = new FireStationsDaoImpl();
        fireStations = new FireStations("stationAddress","7");
    }

    @Test
    public void saveTest(){
        fireStationsDaoImpl.save(fireStations);
        assertTrue(fireStationsDaoImpl.findAll().contains(fireStations));
    }
    //TODO test unitaire par fonction

    @Test
    public void findByIdTest(){
        FireStations newFireStations = new FireStations("newAddress","1");
        fireStationsDaoImpl.save(newFireStations);
        assertEquals(fireStationsDaoImpl.findById("newAddress"),newFireStations);
    }

    @Test
    public void updateTest(){
        fireStations.setStation("4");
        fireStationsDaoImpl.update(fireStations);
        assertEquals("4",fireStations.getStation());
    }

    @Test
    public void deleteTest(){
        saveTest();
        fireStationsDaoImpl.delete("stationAddress");
        assertFalse(fireStationsDaoImpl.findAll().contains(fireStations));
    }

}
