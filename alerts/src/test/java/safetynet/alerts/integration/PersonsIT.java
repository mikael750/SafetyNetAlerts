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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PersonsIT {

    @Autowired
    private PersonsDao personsDao;
    @Autowired
    private FireStationsDao fireStationDao;
    @Autowired
    private MedicalRecordsDao medicalRecordsDao;

    @BeforeAll
    private static void setUp() throws Exception {
    }

    @BeforeEach
    private void setUpPerTest() {
    }

    @Test
    public void personsController_ShouldAddNewPerson(){
        Persons persons = new Persons("Michael","Jackson","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        PersonsController personsController = new PersonsController(personsDao,fireStationDao,medicalRecordsDao);
        personsController.ajouterPersons(persons);
        assertTrue(personsController.listePersons().contains(persons));
        //verify(personsController,);
    }
    //TODO test integration
}
