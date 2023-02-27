package safetynet.alerts.DAO;

import safetynet.alerts.model.MedicalRecords;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MedicalRecordsDao {
    List<MedicalRecords> findAll();
    MedicalRecords findById(String firstName, String lastName);
    MedicalRecords save(MedicalRecords medicalRecords);

    MedicalRecords update(MedicalRecords medicalRecord);

    boolean delete(String firstName, String lastName);
}