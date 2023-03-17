package safetynet.alerts.controller;

import org.springframework.http.HttpStatus;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.model.MedicalRecords;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import safetynet.alerts.model.Persons;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class MedicalRecordsController {

    private final MedicalRecordsDao medicalRecordsDao;

    public MedicalRecordsController(MedicalRecordsDao medicalRecordsDao){
        this.medicalRecordsDao = medicalRecordsDao;
    }

    /**
     * affiche une liste des record medical dans la database
     *
     * @return List MedicalRecords
     */
    @GetMapping(value = "/medicalRecords")
    public List<MedicalRecords> listeMedicalRecords() {
        return medicalRecordsDao.findAll();
    }

    /**
     * affiche les record medical par le nom et le prenom preciser dans lurl
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return MedicalRecord
     */
    @GetMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public MedicalRecords afficherMedicalRecords(@PathVariable String firstName,@PathVariable String lastName) {
        return medicalRecordsDao.findById(firstName, lastName);
    }

    /**
     * ajoute un record medical a la database MedicalRecords
     *
     * @param medicalRecords medicalRecords
     * @return medicalRecordsAdded
     */
    @PostMapping(value = "/medicalRecord")
    public ResponseEntity<MedicalRecords> ajouterMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        MedicalRecords medicalRecordsAdded = medicalRecordsDao.save(medicalRecords);
        if (Objects.isNull(medicalRecordsAdded)) {
            return ResponseEntity.noContent().build();
        }
        /*URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(medicalRecordsAdded.getFirstName(), medicalRecordsAdded.getLastName())
                .toUri();*/
        return ResponseEntity.ok(medicalRecordsAdded);//.created(location).build()
    }

    /**
     * modifies les donnees dun record medical existante
     *
     * @param firstName firstName
     * @param lastName lastName
     * @param medicalRecordsDetails medicalRecordsDetails
     * @return updateMedicalRecords
     * @throws Exception (firstName + lastName + " na pas de donnee medical")
     */
    @PutMapping(value = "/medicalRecord/{firstName}/{lastName}")
     public ResponseEntity<MedicalRecords> updateMedicalRecords(@PathVariable String firstName, @PathVariable String lastName, @RequestBody MedicalRecords medicalRecordsDetails) throws Exception {
        MedicalRecords updateMedicalRecords = medicalRecordsDao.findById(firstName,lastName);
        if (Objects.isNull(updateMedicalRecords)){
            throw new Exception(firstName + lastName + " na pas de donnee medical");
        }
        updateMedicalRecords.setBirthdate(medicalRecordsDetails.getBirthdate());
        updateMedicalRecords.setMedications(medicalRecordsDetails.getMedications());
        updateMedicalRecords.setAllergies(medicalRecordsDetails.getAllergies());

        medicalRecordsDao.update(updateMedicalRecords);

        return ResponseEntity.ok(updateMedicalRecords);
    }

    /**
     * Supprime un record medical de la database
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return HttpStatus
     */
    @DeleteMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<String> deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {

        boolean isDeleted = medicalRecordsDao.delete(firstName, lastName);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(firstName);

    }
}