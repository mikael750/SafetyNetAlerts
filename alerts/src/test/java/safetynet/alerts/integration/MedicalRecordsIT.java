package safetynet.alerts.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.controller.MedicalRecordsController;
import safetynet.alerts.model.MedicalRecords;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicalRecordsIT {

    @Autowired
    private MedicalRecordsDao medicalRecordsDao;

    MedicalRecordsController medicalRecordsController;
    static MedicalRecords test1;
    static MedicalRecords test2;

    @BeforeAll
    private static void setUp() throws Exception {
        test1 = new MedicalRecords("Michael","Jackson","01/01/2000",new ArrayList<>(),new ArrayList<>());
        test2 = new MedicalRecords("Jean","Dujardin","02/02/2002",new ArrayList<>(),new ArrayList<>());

    }

    @BeforeEach
    private void setUpPerTest() {
        medicalRecordsController = new MedicalRecordsController(medicalRecordsDao);
    }

    @Test
    public void medicalRecordsController_ShouldAddNewRecord(){
        medicalRecordsController.ajouterMedicalRecords(test1);
        assertTrue(medicalRecordsController.listeMedicalRecords().contains(test1));
    }

    @Test
    public void medicalRecordsController_ShouldUpdateRecord(){
        medicalRecordsController.ajouterMedicalRecords(test2);
        MedicalRecords recordsDetail = new MedicalRecords("Jean","Dujardin","03/03/2003",new ArrayList<>(),new ArrayList<>());
        try {
            medicalRecordsController.updateMedicalRecords("Jean","Dujardin", recordsDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertSame("03/03/2003", medicalRecordsController.afficherMedicalRecords("Jean", "Dujardin").getBirthdate());
    }

    @Test
    public void medicalRecordsController_ShouldDeleteRecord() {
        medicalRecordsController_ShouldAddNewRecord();
        medicalRecordsController.deleteMedicalRecord("Michael","Jackson");
        assertFalse(medicalRecordsController.listeMedicalRecords().contains(test1));
    }

}
