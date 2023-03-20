package safetynet.alerts.DAO;

import safetynet.alerts.model.MedicalRecords;
import org.springframework.stereotype.Repository;
import safetynet.alerts.model.Persons;

import java.util.List;

public interface MedicalRecordsDao {
    List<MedicalRecords> findAll();
    MedicalRecords findById(String firstName, String lastName);

    List<MedicalRecords> findByAddress(List<Persons> listByAddress);

    MedicalRecords save(MedicalRecords medicalRecords);

    MedicalRecords update(MedicalRecords medicalRecord);

    boolean delete(String firstName, String lastName);
}