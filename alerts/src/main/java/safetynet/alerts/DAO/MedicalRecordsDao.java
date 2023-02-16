package safetynet.alerts.DAO;

import safetynet.alerts.model.MedicalRecords;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MedicalRecordsDao {
    List<MedicalRecords> findAll();
    MedicalRecords findById(int id);
    MedicalRecords save(MedicalRecords medicalRecords);
}