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
import safetynet.alerts.model.Persons;

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

    @BeforeAll
    private static void setUp() throws Exception {
        test1 = new Persons("Michael","Jackson","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        test2 = new Persons("Jean","Dujardin","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");

    }

    @BeforeEach
    private void setUpPerTest() {
        personsController = new PersonsController(personsDao,fireStationDao,medicalRecordsDao);
    }

    @Test
    public void personsController_ShouldAddNewPerson(){
        personsController.ajouterPersons(test1);
        assertTrue(personsController.listePersons().contains(test1));
    }
    //TODO test integration

    @Test
    public void personsController_ShouldUpdatePerson(){
        personsController.ajouterPersons(test2);
        Persons personsDetail = new Persons("Jean","Dujardin","3rd Manor Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        try {
            personsController.updatePersons("Jean","Dujardin", personsDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertSame("3rd Manor Street", personsController.afficherUnePersonne("Jean", "Dujardin").getAddress());
    }

    @Test
    public void personsController_ShouldDeletePerson() {
        personsController_ShouldAddNewPerson();
        personsController.deletePersons("Michael","Jackson");
        assertFalse(personsController.listePersons().contains(test1));
    }

}
