package safetynet.alerts.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.controller.MedicalRecordsController;
import safetynet.alerts.controller.SystemController;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.service.FireStationsDaoImpl;
import safetynet.alerts.service.MedicalRecordsDaoImpl;
import safetynet.alerts.service.PersonsDaoImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicalRecordsIT {

    @Autowired
    private MedicalRecordsController medicalRecordsController;


    @BeforeAll
    private static void setUp() throws IOException {
        SystemController.initDataBase();
        PersonsDaoImpl.load();
        FireStationsDaoImpl.load();
        MedicalRecordsDaoImpl.load();
    }

    @BeforeEach
    private void setUpPerTest() {}

    @Test
    public void medicalRecordsController_ShouldAddNewRecord(){
        var test = new MedicalRecords("Michael","Jackson","01/01/2000",new ArrayList<>(),new ArrayList<>());
        medicalRecordsController.addMedicalRecords(test);
        assertTrue(Objects.requireNonNull(medicalRecordsController.getMedicalRecords().getBody()).contains(test));
    }

    @Test
    public void medicalRecordsController_ShouldUpdateRecord(){
        var test = new MedicalRecords("Jean","Dujardin","02/02/2002",new ArrayList<>(),new ArrayList<>());
        medicalRecordsController.addMedicalRecords(test);
        MedicalRecords recordsDetail = new MedicalRecords("Jean","Dujardin","03/03/2003",new ArrayList<>(),new ArrayList<>());
        try {
            medicalRecordsController.updateMedicalRecords("Jean","Dujardin", recordsDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertSame("03/03/2003", medicalRecordsController.showMedicalRecords("Jean", "Dujardin").getBirthdate());
    }

    @Test
    public void medicalRecordsController_ShouldDeleteRecord() {
        var test = new MedicalRecords("Michael","Jackson","01/01/2000",new ArrayList<>(),new ArrayList<>());
        medicalRecordsController.addMedicalRecords(test);
        medicalRecordsController.deleteMedicalRecord("Michael","Jackson");
        assertFalse(Objects.requireNonNull(medicalRecordsController.getMedicalRecords().getBody()).contains(test));
    }

    @AfterAll
    static void cleanDataBase() throws IOException {
        SystemController.initDataBase();
    }
}
