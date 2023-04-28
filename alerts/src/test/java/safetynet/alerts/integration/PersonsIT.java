package safetynet.alerts.integration;

import java.io.IOException;
import java.util.Objects;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.controller.PersonsController;
import safetynet.alerts.controller.SystemController;
import safetynet.alerts.model.Persons;
import safetynet.alerts.service.FireStationsDaoImpl;
import safetynet.alerts.service.MedicalRecordsDaoImpl;
import safetynet.alerts.service.PersonsDaoImpl;

import java.text.ParseException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonsIT {

    @Autowired
    private PersonsController personsController;

    @BeforeAll
    private static void setUp() throws IOException {
        SystemController.initDataBase();
        PersonsDaoImpl.load();
        FireStationsDaoImpl.load();
        MedicalRecordsDaoImpl.load();
    }

    @Test
    public void personsController_getPersonTest(){
        assertTrue(Objects.requireNonNull(personsController.getPersons().getBody()).size() > 0 );
    }

    @Test
    public void personsController_ShouldGetListForFlood() throws ParseException {
        var listStationNumber = Arrays.asList("1","2");
        var response = personsController.getListForFlood( listStationNumber );
        assertTrue(Objects.requireNonNull(response.getBody()).size() > 0);
    }

    @Test
    public void personsController_ShouldGetAllMailOfCity() {
        var response = personsController.getAllMailOfCity("Culver");
        assertTrue(Objects.requireNonNull(response.getBody()).size() > 0);
    }

    @Test
    public void personsController_ShouldGetListPhoneNumber() {
        var response = personsController.getListPhoneNumber("1");
        assertTrue(Objects.requireNonNull(response.getBody()).size() > 0);
    }

    @Test
    public void personsController_ShouldGetListForFire() throws ParseException {
        var response = personsController.getListForFire( "1509 Culver St" );
        assertTrue(Objects.requireNonNull(response.getBody()).size() > 0);
    }

    @Test
    public void personsController_ShouldGetInfoOnPerson() throws ParseException {
        var response = personsController.getInfoOnPerson("John","Boyd");
        assertTrue(Objects.requireNonNull(response.getBody()).size() > 0);
    }

    @Test
    public void personsController_ShouldGetChildList() throws ParseException {
        var response = personsController.getChildList( "1509 Culver St" );
        assertTrue(Objects.requireNonNull(response.getBody()).size() > 0);
    }

    @Test
    public void personsController_ShouldGetStationNumber() throws ParseException {
        var response = personsController.getStationNumber("1");
        assertTrue((Objects.requireNonNull(response.getBody()).getPersons()).size() > 0);
    }

    @Test
    public void personsController_ShouldAddNewPerson(){
        var test = new Persons("Michael","Jackson","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        personsController.addPersons(test);
        assertTrue(Objects.requireNonNull(personsController.getPersons().getBody()).contains(test));
    }

    @Test
    public void personsController_ShouldUpdatePerson(){
        Persons test = new Persons("Jean","Dujardin","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        personsController.addPersons(test);
        Persons personsDetail = new Persons("Jean","Dujardin","3rd Manor Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        try {
            personsController.updatePersons("Jean","Dujardin", personsDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("3rd Manor Street", personsController.showAPerson("Jean", "Dujardin").getAddress());
    }

    @Test
    public void personsController_ShouldDeletePerson() {
        Persons test = new Persons("Michael","Jackson","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        personsController.addPersons(test);
        personsController.deletePersons("Michael","Jackson");
        assertFalse(Objects.requireNonNull(personsController.getPersons().getBody()).contains(test));
    }

    @AfterAll
    static void cleanDataBase() throws IOException {
        SystemController.initDataBase();
    }
}