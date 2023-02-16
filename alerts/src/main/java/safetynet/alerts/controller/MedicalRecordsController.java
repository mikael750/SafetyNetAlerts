package safetynet.alerts.controller;

import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.model.MedicalRecords;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    @GetMapping(value = "/medicalrecord/{id}")
    public MedicalRecords afficherMedicalRecords(@PathVariable int id) {
        return medicalRecordsDao.findById(id);
    }

    @PostMapping(value = "/medicalrecord")
    public ResponseEntity<MedicalRecords> ajouterMedicalRecords(@RequestBody MedicalRecords medicalRecords) {
        MedicalRecords medicalRecordsAdded = medicalRecordsDao.save(medicalRecords);
        if (Objects.isNull(medicalRecordsAdded)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(medicalRecordsAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}