package safetynet.alerts.controller;

import org.springframework.http.HttpStatus;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.model.FireStations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class FireStationsController {

    private final FireStationsDao fireStationsDao;

    public FireStationsController(FireStationsDao fireStationsDao){
        this.fireStationsDao = fireStationsDao;
    }

    @GetMapping(value = "/firestations")
    public List<FireStations> listeFireStations() {
        return fireStationsDao.findAll();
    }

    @GetMapping(value = "/firestation/{address}")
    public FireStations afficherFireStations(@PathVariable String address) {
        return fireStationsDao.findById(address);
    }

    @PostMapping(value = "/firestation")
    public ResponseEntity<FireStations> ajouterFireStations(@RequestBody FireStations fireStations) {
        FireStations fireStationsAdded = fireStationsDao.save(fireStations);
        if (Objects.isNull(fireStationsAdded)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{address}")
                .buildAndExpand(fireStationsAdded.getAddress())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/firestation/{address}")
    public ResponseEntity<FireStations> updateFireStations(@PathVariable String address, @RequestBody FireStations fireStationsDetails) throws Exception {
        FireStations updateFireStations = fireStationsDao.findById(address);
        if (Objects.isNull(updateFireStations)){
            throw new Exception(address + " n'est pas une bonne adresse");
        }
        updateFireStations.setStation(fireStationsDetails.getStation());

        fireStationsDao.save(updateFireStations);

        return ResponseEntity.ok(updateFireStations);
    }

    @DeleteMapping(value = "/firestation/{address}")
    public ResponseEntity<String> deleteAddress(@PathVariable String address) {

        boolean isDeleted = fireStationsDao.delete(address);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(address);
    }
}