package safetynet.alerts.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.DAO.PersonsDao;
import safetynet.alerts.DAO.Util.tools;
import safetynet.alerts.controller.PersonsController;
import safetynet.alerts.model.Persons;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PersonsIT {

    @Autowired
    private PersonsDao personsDao;
    @Autowired
    private FireStationsDao fireStationDao;
    @Autowired
    private MedicalRecordsDao medicalRecordsDao;

    PersonsController personsController;

    @BeforeAll
    private static void setUp() throws Exception {
    }

    @BeforeEach
    private void setUpPerTest() {
        personsController = new PersonsController(personsDao,fireStationDao,medicalRecordsDao);
    }

    @Test
    public void personsController_ShouldAddNewPerson(){
        Persons test1 = new Persons("Michael","Jackson","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        personsController.ajouterPersons(test1);
        assertTrue(personsController.listePersons().contains(test1));
    }
    //TODO test integration

    @Test
    public void personsController_ShouldUpdatePerson(){
        Persons test2 = new Persons("Jean","Dujardin","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        personsController.ajouterPersons(test2);
        Persons personsDetail = new Persons("Jean","Dujardin","3rd Manor Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        try {
            personsController.updatePersons("Jean","Dujardin", personsDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertSame("3rd Manor Street", personsController.afficherUnePersonne("Jean", "Dujardin").getAddress());
    }

}
