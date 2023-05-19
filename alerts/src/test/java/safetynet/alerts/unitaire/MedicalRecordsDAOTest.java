package safetynet.alerts.unitaire;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import safetynet.alerts.controller.SystemController;
import safetynet.alerts.service.MedicalRecordsDaoImpl;
import safetynet.alerts.model.MedicalRecords;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class MedicalRecordsDAOTest {

    private static MedicalRecordsDaoImpl medicalRecordsDaoImpl;

    @Mock
    private static MedicalRecords medicalRecords;

    @BeforeAll
    private static void setUp() throws Exception {
        SystemController.initDataBase();
    }

    @BeforeEach
    private void setUpPerTest() throws IOException {
        medicalRecordsDaoImpl = new MedicalRecordsDaoImpl();
        medicalRecords = new MedicalRecords("Michael","Jackson","01/01/1987", new ArrayList<>(),new ArrayList<>());
    }

    @Test
    public void saveTest(){
        medicalRecordsDaoImpl.save(medicalRecords);
        assertTrue(medicalRecordsDaoImpl.findAll().contains(medicalRecords));
    }

    @Test
    public void findByIdTest(){
        MedicalRecords newMedicalRecords = new MedicalRecords("Michael","Jordan","01/01/1987", new ArrayList<>(),new ArrayList<>());
        medicalRecordsDaoImpl.save(newMedicalRecords);
        assertEquals(medicalRecordsDaoImpl.findById("Michael","Jordan"),newMedicalRecords);
    }

    @Test
    public void updateTest(){
        medicalRecords.setBirthdate("02/02/1987");
        medicalRecordsDaoImpl.update(medicalRecords);
        assertEquals("02/02/1987",medicalRecords.getBirthdate());
    }

    @Test
    public void deleteTest(){
        saveTest();
        medicalRecordsDaoImpl.delete("Michael","Jackson");
        assertFalse(medicalRecordsDaoImpl.findAll().contains(medicalRecords));
    }

    @AfterAll
    static void cleanDataBase() throws IOException {
        SystemController.initDataBase();
    }
}
