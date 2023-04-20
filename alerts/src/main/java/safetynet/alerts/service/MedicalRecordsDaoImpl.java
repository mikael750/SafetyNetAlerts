package safetynet.alerts.service;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.util.AlertsUtils;
import safetynet.alerts.model.MedicalRecords;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MedicalRecordsDaoImpl implements MedicalRecordsDao {

    public static List<MedicalRecords> medicalRecords = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(MedicalRecordsDaoImpl.class);

    /**
     * Charge les informations de la database firestations
     */
    public static void load(){
        logger.info("Chargement des donner des records medicals.");
        try (InputStream file = MedicalRecordsDaoImpl.class.getResourceAsStream("/saveData.json")){
            assert file != null;
            JsonIterator iter = JsonIterator.parse(file.readAllBytes());
            Any any = iter.readAny();
            Any medicalRecordsAny = any.get("medicalrecords");
            medicalRecordsAny.forEach(a -> {
                medicalRecords.add(new MedicalRecords(a.get("firstName").toString() , a.get("lastName").toString(), a.get("birthdate").toString(), a.get("medications").as(List.class), a.get("allergies").as(List.class)));
            });
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<MedicalRecords> findAll() {
        logger.info("Recherche de toutes les records medicals.");
        return medicalRecords;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public MedicalRecords findById(String firstName, String lastName) {
        logger.info("Recherche d'un record medical par nom et prenom.");
        for (MedicalRecords medicalRecord : medicalRecords){
            if (Objects.equals(medicalRecord.getFirstName(), firstName) && Objects.equals(medicalRecord.getLastName(), lastName)){
                return medicalRecord;
            }
        }
        return null;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public MedicalRecords save(MedicalRecords medicalRecord) {
        logger.info("Sauvegarde des changements de la Dao des record medical");
        medicalRecords.add(medicalRecord);
        AlertsUtils.writeJsonFile();
        return medicalRecord;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public MedicalRecords update(MedicalRecords medicalRecord) {
        logger.info("Mis à Jour de la Dao des records medical");
        medicalRecords.remove(medicalRecord);
        medicalRecords.add(medicalRecord);
        AlertsUtils.writeJsonFile();
        return medicalRecord;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean delete(String firstName, String lastName) {
        logger.info("Suppression des record medical d'une personne");
        boolean isDeleted = medicalRecords.removeIf((medicalRecords -> Objects.equals(medicalRecords.getFirstName(), firstName) && Objects.equals(medicalRecords.getLastName(), lastName)));

        AlertsUtils.writeJsonFile();
        return isDeleted;
    }
}