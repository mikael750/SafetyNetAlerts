package safetynet.alerts.unitaire;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.service.FireStationsDaoImpl;
import safetynet.alerts.DAO.PersonsDao;
import safetynet.alerts.model.FireStations;
import safetynet.alerts.model.Persons;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FireStationsDAOTest {

    private static FireStationsDaoImpl fireStationsDaoImpl;

    @Mock
    private static FireStations fireStations;

    @Mock
    private static PersonsDao personsDao;

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

    @Test
    public void findAddressByStationTest(){
        List<String> listString = fireStationsDaoImpl.findAddressByStation("1");
        assertNotNull(listString);
    }

    @Test
    public void findByNumberStationTest(){
        List<Persons> listPersons = fireStationsDaoImpl.findByNumberStation("1",personsDao);
        assertNotNull(listPersons);
    }
}
