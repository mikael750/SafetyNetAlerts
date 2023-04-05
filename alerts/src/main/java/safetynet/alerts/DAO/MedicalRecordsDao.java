package safetynet.alerts.DAO;

import safetynet.alerts.model.MedicalRecords;

import java.util.List;

public interface MedicalRecordsDao {

    /**
     * Trouve tous les records medical
     *
     * @return medicalRecords
     */
    List<MedicalRecords> findAll();

    /**
     * Trouve un record medical par nom et prénom
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return medicalRecord
     */
    MedicalRecords findById(String firstName, String lastName);

    /**
     * Sauvegarde les changements de la base de donner des record medical
     *
     * @param medicalRecord medicalRecord
     * @return medicalRecord
     */
    MedicalRecords save(MedicalRecords medicalRecord);

    /**
     * Mis à Jour de la base de donner des records medical
     *
     * @param medicalRecord medicalRecord
     * @return medicalRecord
     */
    MedicalRecords update(MedicalRecords medicalRecord);

    /**
     * Supprime le record medical d'une personne
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return boolean
     */
    boolean delete(String firstName, String lastName);
}