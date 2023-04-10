package safetynet.alerts.unitaire;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.DAO.PersonsDaoImpl;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;
import safetynet.alerts.model.response.AddressList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonsDAOTest {

    private static PersonsDaoImpl personsDaoImpl;

    @Mock
    private static Persons persons;

    @Mock
    private static FireStationsDao fireStationsDao;

    @Mock
    private static MedicalRecordsDao medicalRecordsDao;

    @BeforeAll
    private static void setUp() throws Exception {
        PersonsDaoImpl.load();
    }

    @BeforeEach
    private void setUpPerTest() {
        personsDaoImpl = new PersonsDaoImpl();
        persons = new Persons("Michael","Jackson","HisAddress","OldCity","zip","555","not@hisemail.com");
    }

    @Test
    public void saveTest(){
        personsDaoImpl.save(persons);
        assertTrue(personsDaoImpl.findAll().contains(persons));
    }
    //TODO test unitaire par fonction

    @Test
    public void findByIdTest(){
        Persons newPersons = new Persons("Michael","Jordan","HisAddress","OldCity","zip","555","not@hisemail.com");
        personsDaoImpl.save(newPersons);
        assertEquals(personsDaoImpl.findById("Michael","Jordan"),newPersons);
    }

    @Test
    public void updateTest(){
        persons.setCity("newCity");
        personsDaoImpl.update(persons);
        assertEquals("newCity",persons.getCity());
    }

    @Test
    public void deleteTest(){
        saveTest();
        personsDaoImpl.delete("Michael","Jackson");
        assertFalse(personsDaoImpl.findAll().contains(persons));
    }

    @Test
    public void findByNamesTest(){
        saveTest();
        List<Persons> listPersons = personsDaoImpl.findByNames("Michael","Jackson");
        assertNotNull(listPersons);
    }

    @Test
    public void findPersonsAgesTest() throws ParseException {
        List<MedicalRecords> listRecord = new ArrayList<>();
        List<Persons> listPersons = new ArrayList<>();
        List<String> list = personsDaoImpl.findPersonsAges(listRecord,listPersons);
        assertNotNull(list);
    }

    @Test
    public void findByCityTest(){
        saveTest();
        List<Persons> list = personsDaoImpl.findByCity("OldCity");
        assertNotNull(list);
    }

    @Test
    public void findByAddressTest(){
        saveTest();
        List<Persons> list = personsDaoImpl.findByAddress("HisAddress");
        assertNotNull(list);
    }

    @Test
    public void findAddressFoyerTest() throws ParseException {
        List<String> listString = Arrays.asList("1","2","3","4");
        List<AddressList> listAddressFoyer = personsDaoImpl.findAddressFoyer(listString,fireStationsDao,medicalRecordsDao);
        assertNotNull(listAddressFoyer);
    }

}
