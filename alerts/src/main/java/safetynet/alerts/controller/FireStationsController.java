package safetynet.alerts.controller;

import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.model.FireStations;
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
public class FireStationsController {

    private final FireStationsDao fireStationsDao;

    public FireStationsController(FireStationsDao fireStationsDao){
        this.fireStationsDao = fireStationsDao;
    }

    @GetMapping(value = "/firestation")
    public List<FireStations> listeFireStations() {
        return fireStationsDao.findAll();
    }

    @GetMapping(value = "/firestation/{id}")
    public FireStations afficherFireStations(@PathVariable int id) {
        return fireStationsDao.findById(id);
    }

    @PostMapping(value = "/firestation")
    public ResponseEntity<FireStations> ajouterFireStations(@RequestBody FireStations fireStations) {
        FireStations fireStationsAdded = fireStationsDao.save(fireStations);
        if (Objects.isNull(fireStationsAdded)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fireStationsAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}