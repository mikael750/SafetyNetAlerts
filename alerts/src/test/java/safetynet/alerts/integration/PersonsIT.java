package safetynet.alerts.integration;

import java.io.IOException;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.DAO.PersonsDao;
import safetynet.alerts.controller.PersonsController;
import safetynet.alerts.controller.SystemController;
import safetynet.alerts.model.Persons;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import safetynet.alerts.service.FireStationsDaoImpl;
import safetynet.alerts.service.MedicalRecordsDaoImpl;
import safetynet.alerts.service.PersonsDaoImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@Disabled
@SpringBootTest
public class PersonsIT {

    @Autowired
    private PersonsDao personsDao;
    @Autowired
    private FireStationsDao fireStationDao;
    @Autowired
    private MedicalRecordsDao medicalRecordsDao;


    @Autowired
    private PersonsController personsController;

    //private Persons test1 = new Persons("Michael","Jackson","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
    //private Persons test2 = new Persons("Jean","Dujardin","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
    /* private MedicalRecords medicalRecord1 = new MedicalRecords("Michael","Jackson","01/01/2020",new ArrayList<>(),new ArrayList<>());
    private FireStations fireStations1 = new FireStations("2nd House Street","1");
*/
    @BeforeAll
    private static void setUp() throws IOException{
		SystemController.initDataBase();
		PersonsDaoImpl.load();
		FireStationsDaoImpl.load();
		MedicalRecordsDaoImpl.load();
	}

	@Test
	public void getPersonTest(){
		assertTrue(personsController.getPersons().getBody().size() > 0 );
	}

    @Test
    public void personsController_ShouldGetListForFlood() throws ParseException {
        List<String> listStationNumber = Arrays.asList("1","2");
        var response = personsController.getListForFlood( listStationNumber );
        assertThat("200 OK").isEqualTo(response.getStatusCode().toString());
    }

    @Test
    public void personsController_ShouldGetAllMailOfCity() {
        //assertNotNull(personsController.getAllMailOfCity("Brooklyn"));
        var response = personsController.getAllMailOfCity("Brooklyn");
        assertThat("200 OK").isEqualTo(response.getStatusCode().toString());
    }

    @Test
    public void personsController_ShouldGetListPhoneNumber() {
        //personsController.addPersons(test1);
        var response = personsController.getListPhoneNumber("1");
        assertThat("200 OK").isEqualTo(response.getStatusCode().toString());
        //assertNotNull();
    }

    @Test
    public void personsController_ShouldGetListForFire() throws ParseException {
        //personsController.addPersons(test1);//
        //assertTrue( Objects.requireNonNull( personsController.getListForFire( "1509 Culver St" ).getBody() ).size() > 0 );
        var response = personsController.getListForFire( "1509 Culver St" );
        assertThat("200 OK").isEqualTo(response.getStatusCode().toString());

    }

    @Test
    public void personsController_ShouldGetInfoOnPerson() throws ParseException {
        //personsController.addPersons(test1);
        //assertTrue(Objects.requireNonNull(personsController.getInfoOnPerson("John","Boyd").getBody() ).size() > 0);
        var response = personsController.getInfoOnPerson("John","Boyd");
        assertThat("200 OK").isEqualTo(response.getStatusCode().toString());
    }

    @Test
    public void personsController_ShouldGetChildList() throws ParseException {
        //personsController.addPersons(test1);
        //assertTrue( Objects.requireNonNull( personsController.getChildList( "1509 Culver St" ).getBody() ).size() > 0);
        var response = personsController.getChildList( "1509 Culver St" );
        assertThat("200 OK").isEqualTo(response.getStatusCode().toString());
    }

    @Test
    public void personsController_ShouldGetStationNumber() throws ParseException {
        //personsController.addPersons(test1);
        //assertNotNull(personsController.getStationNumber("1"));
        var response = personsController.getStationNumber("1");
        assertThat("200 OK").isEqualTo(response.getStatusCode().toString());
    }

	//a corriger
    /*@Test*/
 /*   public void personsController_ShouldAddNewPerson(){
        Persons test = new Persons("Michael","Jackson","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        personsController.addPersons(test);
        assertTrue(personsController.getPersons().contains(test));
    }*/

    @Test
    public void personsController_ShouldUpdatePerson(){
        //personsController.addPersons(test2);
        Persons test = new Persons("Jean","Dujardin","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        personsController.addPersons(test);
        Persons personsDetail = new Persons("Jean","Dujardin","3rd Manor Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        try {
            personsController.updatePersons("Jean","Dujardin", personsDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("3rd Manor Street", personsController.showAPerson("Jean","Dujardin").getAddress());
    }

  /*  @Test
    public void personsController_ShouldDeletePerson() {
        Persons test = new Persons("Michael","Jackson","2nd House Street","Brooklyn","123-4567","555-04-02-07","MicSon@notmail.com");
        personsController_ShouldAddNewPerson();
        personsController.deletePersons("Michael","Jackson");
        assertFalse(personsController.getPersons().contains(test));
    }*/

    @AfterAll
    static void afterAll() throws IOException {
        SystemController.initDataBase();
    }
}