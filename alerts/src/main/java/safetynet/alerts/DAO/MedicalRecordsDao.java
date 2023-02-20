package safetynet.alerts.DAO;

import safetynet.alerts.model.MedicalRecords;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MedicalRecordsDao {
    List<MedicalRecords> findAll();
    MedicalRecords findById(String firstName);
    MedicalRecords save(MedicalRecords medicalRecords);
    boolean delete(String firstName);
}