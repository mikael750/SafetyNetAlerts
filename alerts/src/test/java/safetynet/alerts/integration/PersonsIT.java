package safetynet.alerts.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.DAO.PersonsDao;
import safetynet.alerts.controller.PersonsController;
import safetynet.alerts.model.FireStations;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonsIT {

    @Autowired
    private PersonsDao personsDao;
    @Autowired
    private FireStationsDao fireStationDao;
    @Autowired
    private MedicalRecordsDao medicalRecordsDao;

    PersonsController personsController;
    static Persons test1;
    static Persons test2;
    static MedicalRecords medicalRecord1;
    static FireStations fireStations1;

    @BeforeAll
    private static void setUp() throws Exception {
        test1 = new Persons("Michael","Jackson","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        test2 = new Persons("Jean","Dujardin","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        medicalRecord1 = new MedicalRecords("Michael","Jackson","01/01/2020",new ArrayList<>(),new ArrayList<>());
        fireStations1 = new FireStations("2nd House Street","1");
    }

    @BeforeEach
    private void setUpPerTest() {
        medicalRecordsDao.save(medicalRecord1);
        fireStationDao.save(fireStations1);
        personsController = new PersonsController(personsDao,fireStationDao,medicalRecordsDao);
    }

    @Test
    public void personsController_ShouldAddNewPerson(){
        personsController.addPersons(test1);
        assertTrue(personsController.getPersons().contains(test1));
    }
    //TODO test integration

    @Test
    public void personsController_ShouldUpdatePerson(){
        personsController.addPersons(test2);
        Persons personsDetail = new Persons("Jean","Dujardin","3rd Manor Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        try {
            personsController.updatePersons("Jean","Dujardin", personsDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertSame("3rd Manor Street", personsController.showAPerson("Jean", "Dujardin").getAddress());
    }

    @Test
    public void personsController_ShouldDeletePerson() {
        personsController_ShouldAddNewPerson();
        personsController.deletePersons("Michael","Jackson");
        assertFalse(personsController.getPersons().contains(test1));
    }

    @Test
    public void personsController_ShouldGetListForFlood() throws ParseException {
        List<String> listStationNumber = Arrays.asList("1","2");
        assertNotNull(personsController.getListForFlood(listStationNumber));
    }

    @Test
    public void personsController_ShouldGetAllMailOfCity() {
        assertNotNull(personsController.getAllMailOfCity("Brooklyn"));
    }

    @Test
    public void personsController_ShouldGetListPhoneNumber() {
        personsController.addPersons(test1);
        assertNotNull(personsController.getListPhoneNumber("1"));
    }

    @Test
    public void personsController_ShouldGetListForFire() throws ParseException {
        assertNotNull(personsController.getListForFire("2nd House Street"));
    }

    @Test
    public void personsController_ShouldGetInfoOnPerson() throws ParseException {
        personsController.addPersons(test1);
        assertNotNull(personsController.getInfoOnPerson("Michael","Jackson"));
    }

    @Test
    public void personsController_ShouldGetChildList() throws ParseException {
        personsController.addPersons(test1);
        assertNotNull(personsController.getChildList("2nd House Street"));
    }

    @Test
    public void personsController_ShouldGetStationNumber() throws ParseException {
        personsController.addPersons(test1);
        assertNotNull(personsController.getStationNumber("1"));
    }
}
