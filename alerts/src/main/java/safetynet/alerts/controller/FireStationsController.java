package safetynet.alerts.controller;

import org.springframework.http.HttpStatus;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.model.FireStations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

@RestController
public class FireStationsController {

    private final FireStationsDao fireStationsDao;

    public FireStationsController(FireStationsDao fireStationsDao){
        this.fireStationsDao = fireStationsDao;
    }

    /**
     * affiche une liste des Stations dans la database
     *
     * @return List FireStations
     */
    @GetMapping(value = "/fireStation")
    public List<FireStations> getFireStations() {
        return fireStationsDao.findAll();
    }

    /**
     * affiche les stations par l'adresse preciser dans l'URL
     *
     * @param address address
     * @return FireStation
     */
    @GetMapping(value = "/firestation/{address}")
    public FireStations showFireStations(@PathVariable String address) {
        return fireStationsDao.findById(address);
    }

    /**
     * ajoute une station a la database FireStation
     *
     * @param fireStations firestation
     * @return fireStationsAdded
     */
    @PostMapping(value = "/firestation")
    public ResponseEntity<FireStations> addFireStations(@RequestBody FireStations fireStations) {
        FireStations fireStationsAdded = fireStationsDao.save(fireStations);
        if (Objects.isNull(fireStationsAdded)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fireStationsAdded);
    }

    /**
     * modifies les donnees dune station existante
     *
     * @param address address
     * @param fireStationsDetails fireStationsDetails
     * @return updateFireStations
     * @throws Exception (address + " n'est pas une bonne adresse")
     */
    @PutMapping(value = "/firestation/{address}")
    public ResponseEntity<FireStations> updateFireStations(@PathVariable String address, @RequestBody FireStations fireStationsDetails) throws Exception {
        FireStations updateFireStations = fireStationsDao.findById(address);
        if (Objects.isNull(updateFireStations)){
            throw new Exception(address + " n'est pas une bonne adresse");
        }
        updateFireStations.setStation(fireStationsDetails.getStation());

        fireStationsDao.update(updateFireStations);

        return ResponseEntity.ok(updateFireStations);
    }

    /**
     * Supprime une station et son adresse de la database
     *
     * @param address address
     * @return HttpStatus
     */
    @DeleteMapping(value = "/firestation/{address}")
    public ResponseEntity<String> deleteAddress(@PathVariable String address) {

        boolean isDeleted = fireStationsDao.delete(address);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(address);
    }
}