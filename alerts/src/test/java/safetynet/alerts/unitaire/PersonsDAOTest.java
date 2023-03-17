package safetynet.alerts.unitaire;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.DAO.PersonsDao;
import safetynet.alerts.DAO.PersonsDaoImpl;
import safetynet.alerts.controller.PersonsController;
import safetynet.alerts.model.Persons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PersonsDAOTest {

    private static PersonsDaoImpl personsDaoImpl;

    @Mock
    private static Persons persons;

    @BeforeAll
    private static void setUp() throws Exception {
    }

    @BeforeEach
    private void setUpPerTest() {
        personsDaoImpl = new PersonsDaoImpl();
    }

    @Test
    public void saveTest(){
        Persons persons = new Persons("Michael","Jackson","","","","","");
        personsDaoImpl.save(persons);
        assertTrue(personsDaoImpl.findAll().contains(persons));
    }
    //TODO test unitaire par fonction
}
