package safetynet.alerts.unitaire;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.controller.PersonsController;
import safetynet.alerts.controller.SystemController;
import safetynet.alerts.model.FireStations;
import safetynet.alerts.service.PersonsDaoImpl;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;
import safetynet.alerts.model.response.AddressList;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
		PersonsDaoImpl.load();
    }

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
        saveTest();
        List<Persons> listPersons = personsDaoImpl.findByNames("Michael","Jackson");
        assertTrue(listPersons.size() > 0);
    }

    @Test
    public void findPersonsAgesTest() throws ParseException {
        var medicalRecord = new MedicalRecords("Michael","Jackson","01/01/1987", new ArrayList<>(),new ArrayList<>());
        List<MedicalRecords> listRecord = List.of(medicalRecord);
        List<Persons> listPersons = List.of(persons);
        List<String> list = personsDaoImpl.findPersonsAges(listRecord,listPersons);
        assertTrue(list.size() > 0 );
    }

    @Test
    public void findByAddressTest(){
        saveTest();
        List<Persons> list = personsDaoImpl.findByAddress("HisAddress");
        assertTrue(list.size() > 0);
    }

    @Test
    public void updateTest(){
        saveTest();
        Persons person = new Persons("Michael","Jackson","HisAddress","OldCity","zip","555","not@hisemail.com");
        person.setCity("newCity");
        personsDaoImpl.update(person);
        assertEquals("newCity",person.getCity());
    }

    @Test
    public void findByCityTest(){
        saveTest();
        List<Persons> list = personsDaoImpl.findByCity("OldCity");
        assertTrue(list.size() > 0 );
    }

    @Test
    public void findByIdTest(){
        //Persons newPersons = new Persons("Michael","Jordan","HisAddress","OldCity","zip","555","not@hisemail.com");
        //personsDaoImpl.save(newPersons);
        //assertEquals(personsDaoImpl.findById("Michael","Jordan"),newPersons);
        saveTest();
        assertEquals(personsDaoImpl.findById("Michael","Jackson"), persons);
    }

    @Test
    public void findAddressFoyerTest() throws ParseException {
        personsDaoImpl = new PersonsDaoImpl();
        List<String> listString = Arrays.asList("1","2");
        List<AddressList> listAddressFoyer = new ArrayList<>();
        when(personsDaoImpl.findAddressFoyer(listString, fireStationsDao, medicalRecordsDao)).thenReturn(listAddressFoyer);
        //assertTrue(listAddressFoyer.size() > 0);
    }

    @Test
    public void deleteTest(){
        saveTest();
        Persons person = new Persons("Michael","Jackson","HisAddress","OldCity","zip","555","not@hisemail.com");
        personsDaoImpl.delete("Michael","Jackson");
        assertFalse(personsDaoImpl.findAll().contains(person));
    }

}