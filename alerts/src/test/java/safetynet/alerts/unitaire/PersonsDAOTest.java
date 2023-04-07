package safetynet.alerts.unitaire;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.DAO.PersonsDaoImpl;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class PersonsDAOTest {

    private static PersonsDaoImpl personsDaoImpl;

    @Mock
    private static Persons persons;

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
    /*
    @Test
    public void findPersonsAgesTest() throws ParseException {
        List<MedicalRecords> listRecord = null;
        List<Persons> listPersons = null;
        personsDaoImpl.findPersonsAges(listRecord,listPersons);
        
    }*/

}
