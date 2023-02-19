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

    @GetMapping(value = "/medicalrecord")
    public List<MedicalRecords> listeMedicalRecords() {
        return medicalRecordsDao.findAll();
    }

    @GetMapping(value = "/medicalrecord/{firstName}")
    public MedicalRecords afficherMedicalRecords(@PathVariable String firstName) {
        return medicalRecordsDao.findById(firstName);
    }

    @PostMapping(value = "/medicalrecord")
    public ResponseEntity<MedicalRecords> ajouterMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        MedicalRecords medicalRecordsAdded = medicalRecordsDao.save(medicalRecords);
        if (Objects.isNull(medicalRecordsAdded)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}")
                .buildAndExpand(medicalRecordsAdded.getFirstName())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/medicalrecord/{firstName}")
    public ResponseEntity<MedicalRecords> updateMedicalRecords(@PathVariable String firstName, @RequestBody MedicalRecords medicalRecordsDetails) throws Exception {
        MedicalRecords updateMedicalRecords = medicalRecordsDao.findById(firstName);
        if (Objects.isNull(updateMedicalRecords)){
            throw new Exception(firstName + " na pas de donne medical");
        }
        updateMedicalRecords.setLastName(medicalRecordsDetails.getLastName());
        updateMedicalRecords.setBirthdate(medicalRecordsDetails.getBirthdate());
        updateMedicalRecords.setMedications(medicalRecordsDetails.getMedications());
        updateMedicalRecords.setAllergies(medicalRecordsDetails.getAllergies());

        medicalRecordsDao.save(updateMedicalRecords);

        return ResponseEntity.ok(updateMedicalRecords);
    }

    @DeleteMapping(value = "/medicalrecord/{firstName}")
    public ResponseEntity<String> deleteMedicalrecord(@PathVariable String firstName) {

        boolean isDeleted = medicalRecordsDao.delete(firstName);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(firstName);

    }
}