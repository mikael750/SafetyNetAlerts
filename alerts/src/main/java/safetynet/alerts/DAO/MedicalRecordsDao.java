package safetynet.alerts.DAO;

import safetynet.alerts.model.MedicalRecords;
import org.springframework.stereotype.Repository;
import safetynet.alerts.model.Persons;

import java.util.List;

public interface MedicalRecordsDao {

    /**
     * @return
     */
    List<MedicalRecords> findAll();

    /**
     * @param firstName
     * @param lastName
     * @return
     */
    MedicalRecords findById(String firstName, String lastName);

    /**
     * @param listByAddress
     * @return
     */
    List<MedicalRecords> findByAddress(List<Persons> listByAddress);

    /**
     * @param medicalRecords
     * @return
     */
    MedicalRecords save(MedicalRecords medicalRecords);

    /**
     * @param medicalRecord
     * @return
     */
    MedicalRecords update(MedicalRecords medicalRecord);

    /**
     * @param firstName
     * @param lastName
     * @return
     */
    boolean delete(String firstName, String lastName);
}