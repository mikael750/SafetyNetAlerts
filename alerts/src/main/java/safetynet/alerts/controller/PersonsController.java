package safetynet.alerts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.DAO.PersonsDao;
import safetynet.alerts.model.FireStations;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import safetynet.alerts.model.response.ChildAlert;
import safetynet.alerts.model.response.CountPersons;
import safetynet.alerts.model.response.SimplePerson;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static safetynet.alerts.DAO.Util.tools.deleteDoublon;

@RestController
public class PersonsController {

    private final PersonsDao personsDao;
    private final FireStationsDao fireStationDao;
    private final MedicalRecordsDao medicalRecordsDao;

    public PersonsController(PersonsDao personsDao, FireStationsDao fireStationsDao, FireStationsDao fireStation, FireStationsDao fireStationDao,  MedicalRecordsDao medicalRecordsDao){
        this.personsDao = personsDao;
        this.fireStationDao = fireStationDao;
        this.medicalRecordsDao = medicalRecordsDao;
    }

    @GetMapping(value = "/persons")
    public List<Persons> listePersons() {
        return personsDao.findAll();
    }

    @GetMapping(value = "/person/{firstName}/{lastName}")
    public Persons afficherUnePersonne(@PathVariable String firstName, @PathVariable String lastName) {
        return personsDao.findById(firstName, lastName);
    }

    @PostMapping(value = "/person")
    public ResponseEntity<Persons> ajouterPersons(@RequestBody Persons persons) {
        Persons personsAdded = personsDao.save(persons);
        if (Objects.isNull(personsAdded)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(personsAdded.getFirstName(),personsAdded.getLastName())//, lastName
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Persons> updatePersons(@PathVariable String firstName, @PathVariable String lastName,@RequestBody Persons personsDetails) throws Exception {
        Persons updatePersons = personsDao.findById(firstName, lastName);
        if (Objects.isNull(updatePersons)){
            throw new Exception(firstName + " n'est pas inscrit");
        }
        //updatePersons.setFirstName(personsDetails.getFirstName());
        //updatePersons.setLastName(personsDetails.getLastName());
        updatePersons.setAddress(personsDetails.getAddress());
        updatePersons.setCity(personsDetails.getCity());
        updatePersons.setZip(personsDetails.getZip());
        updatePersons.setPhone(personsDetails.getPhone());
        updatePersons.setEmail(personsDetails.getEmail());

        personsDao.update(updatePersons);

        return ResponseEntity.ok(updatePersons);
    }

    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<String> deletePersons(@PathVariable String firstName, @PathVariable String lastName) {

        boolean isDeleted = personsDao.delete(firstName, lastName);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(firstName);

    }

    /*
    * Retourne une liste des personnes couvertes par la caserne de pompiers correspondante.
    * Fourni un décompte du nombre d'adultes et du nombre d'enfants dans la zone desservie.
    */
    @GetMapping(value = "/firestation")
    public ResponseEntity getStationNumber(@RequestParam String stationNumber) throws ParseException {
        List<FireStations> findFireStations = fireStationDao.findAll();
        List<MedicalRecords> findMedicalRecords = medicalRecordsDao.findAll();
        List<Persons> listPersonsStations = personsDao.findByFireStation(findFireStations,stationNumber,personsDao);
        List<String> ages = personsDao.findPersonsAges(findMedicalRecords,listPersonsStations);
        int children = 0;
        int adults = 0;
        for (String age : ages){
            if (Integer.parseInt(age) < 18){children++;}else{adults++;}
        }
        CountPersons result = new CountPersons();
        result.setPersons(listPersonsStations.stream().map((x)-> new SimplePerson(x)).toList());
        result.setAdults(adults);
        result.setChildren(children);
        return  ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /*
     * retourne une liste d'enfants habitant à cette adresse.
     */
    @GetMapping(value = "/childAlert")
    public ResponseEntity getChildList(@RequestParam String address) throws ParseException {

        List<Persons> persons = personsDao.findAll();
        List<MedicalRecords> findBirthDate = medicalRecordsDao.findAll();
        List<Persons> listByAddress = personsDao.findByAddress(address);
        List<String> personsAgesInAddress = personsDao.findPersonsAges(findBirthDate,listByAddress);
        List<Persons> foyer = new ArrayList<>();
        List<ChildAlert> childList = new ArrayList<>();
        int i = 0;
        for (String age : personsAgesInAddress){
            if (Integer.parseInt(age) < 18){
                for (Persons person : listByAddress){
                    for (Persons habitant: persons){
                        if(Objects.equals(person.getAddress(),habitant.getAddress())){
                            foyer.add(habitant);
                        }
                    }
                }
                deleteDoublon(foyer);
                childList.add(new ChildAlert(listByAddress.get(i), age, foyer));
            }i++;
        }
        return  ResponseEntity.status(HttpStatus.OK).body(childList);
    }

}