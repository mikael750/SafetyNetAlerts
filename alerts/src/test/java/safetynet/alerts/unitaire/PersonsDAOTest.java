package safetynet.alerts.unitaire;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.controller.SystemController;
import safetynet.alerts.service.PersonsDaoImpl;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;
import safetynet.alerts.model.response.AddressList;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
        SystemController.initDataBase();
        PersonsDaoImpl.load();    }

    @BeforeEach
    private void setUpPerTest() throws IOException {
        personsDaoImpl = new PersonsDaoImpl();
        persons = new Persons("Michael","Jackson","HisAddress","OldCity","zip","555","not@hisemail.com");
    }

    @Test
    public void saveTest(){
        personsDaoImpl.save(persons);
        assertTrue(personsDaoImpl.findAll().contains(persons));
    }

    @Test
    public void findByNamesTest(){
        personsDaoImpl.save(persons);
        var listPersons = personsDaoImpl.findByNames("Michael","Jackson");
        assertTrue(listPersons.size() > 0);
    }

    @Test
    public void findPersonsAgesTest() throws ParseException {
        var medicalRecord = new MedicalRecords("Michael","Jackson","01/01/1987", new ArrayList<>(),new ArrayList<>());
        var listRecord = List.of(medicalRecord);
        var listPersons = List.of(persons);
        var list = personsDaoImpl.findPersonsAges(listRecord,listPersons);
        assertTrue(list.size() > 0 );
    }

    @Test
    public void findByAddressTest(){
        personsDaoImpl.save(persons);
        List<Persons> list = personsDaoImpl.findByAddress("HisAddress");
        assertTrue(list.size() > 0);
    }

    @Test
    public void updateTest(){
        Persons person = new Persons("Michael","Jackson","HisAddress","OldCity","zip","555","not@hisemail.com");
        person.setCity("newCity");
        personsDaoImpl.update(person);
        assertEquals("newCity",person.getCity());
    }

    @Test
    public void findByCityTest(){
        personsDaoImpl.save(persons);
        List<Persons> list = personsDaoImpl.findByCity("OldCity");
        assertTrue(list.size() > 0 );
    }

    @Test
    public void findByIdTest(){
        personsDaoImpl.save(persons);
        assertEquals(personsDaoImpl.findById("Michael","Jackson"), persons);
    }

    @Test
    public void findAddressFoyerTest() throws ParseException {
        personsDaoImpl = new PersonsDaoImpl();
        var listString = Arrays.asList("1","2");
        List<AddressList> listAddressFoyer = new ArrayList<>();
        when(personsDaoImpl.findAddressFoyer(listString, fireStationsDao, medicalRecordsDao)).thenReturn(listAddressFoyer);
    }

    @Test
    public void deleteTest(){
        personsDaoImpl.save(persons);
        personsDaoImpl.delete("Michael","Jackson");
        assertFalse(personsDaoImpl.findAll().contains(persons));
    }

    @AfterAll
    static void cleanDataBase() throws IOException {
        SystemController.initDataBase();
    }
}