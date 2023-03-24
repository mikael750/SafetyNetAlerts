package safetynet.alerts.controller;

import org.springframework.http.HttpStatus;
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

import safetynet.alerts.model.response.ChildAlert;
import safetynet.alerts.model.response.CountPersons;
import safetynet.alerts.model.response.SimplePerson;

import java.text.ParseException;
import java.util.*;

import static safetynet.alerts.DAO.Util.tools.deleteDoublon;

@RestController
public class PersonsController {

    private PersonsDao personsDao;
    private FireStationsDao fireStationDao;
    private MedicalRecordsDao medicalRecordsDao;

    public PersonsController(PersonsDao personsDao, FireStationsDao fireStationDao,  MedicalRecordsDao medicalRecordsDao){
        this.personsDao = personsDao;
        this.fireStationDao = fireStationDao;
        this.medicalRecordsDao = medicalRecordsDao;
    }

    /**
     * affiche une liste des personnes dans la database
     *
     * @return List Persons
     */
    @GetMapping(value = "/persons")
    public List<Persons> listePersons() {
        return personsDao.findAll();
    }

    /**
     * affiche la personne et ces donnee preciser dans l'url
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return Persons
     */
    @GetMapping(value = "/person/{firstName}/{lastName}")
    public Persons afficherUnePersonne(@PathVariable String firstName, @PathVariable String lastName) {
        return personsDao.findById(firstName, lastName);
    }

    /**
     * ajoute une personne a la database Persons
     *
     * @param persons persons
     * @return personsAdded
     */
    @PostMapping(value = "/person")
    public ResponseEntity<Persons> ajouterPersons(@RequestBody Persons persons) {
        Persons personsAdded = personsDao.save(persons);
        if (Objects.isNull(personsAdded)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(personsAdded);
    }

    /**
     * modifies les donnees dune personne existante
     *
     * @param firstName firstName
     * @param lastName lastName
     * @param personsDetails personsDetails
     * @return updatePersons
     * @throws Exception (firstName + " n'est pas inscrit")
     */
    @PutMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Persons> updatePersons(@PathVariable String firstName, @PathVariable String lastName,@RequestBody Persons personsDetails) throws Exception {
        Persons updatePersons = personsDao.findById(firstName, lastName);
        if (Objects.isNull(updatePersons)){
            throw new Exception(firstName + " n'est pas inscrit");
        }
        updatePersons.setAddress(personsDetails.getAddress());
        updatePersons.setCity(personsDetails.getCity());
        updatePersons.setZip(personsDetails.getZip());
        updatePersons.setPhone(personsDetails.getPhone());
        updatePersons.setEmail(personsDetails.getEmail());

        personsDao.update(updatePersons);

        return ResponseEntity.ok(updatePersons);
    }

    /**
     * Supprime une personne de la database
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return HttpStatus
     */
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<String> deletePersons(@PathVariable String firstName, @PathVariable String lastName) {

        boolean isDeleted = personsDao.delete(firstName, lastName);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(firstName);

    }

    /**
     * Retourne une liste des personnes couvertes par la caserne de pompiers correspondante.
     * Fourni un décompte du nombre d'adultes et du nombre d'enfants dans la zone desservie.
     *
     * @param stationNumber stationNumber
     * @return result
     * @throws ParseException
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

    /**
     * retourne une liste d'enfants habitant a cette adresse ainsi que les personnes habitant le foyer
     *
     * @param address address
     * @return childList
     * @throws ParseException
     */
    @GetMapping(value = "/childAlert")
    public ResponseEntity getChildList(@RequestParam String address) throws ParseException {
        //List<MedicalRecords> findBirthDate = medicalRecordsDao.findAll();//findByNameFirstName
        List<Persons> listByAddress = personsDao.findByAddress(address);
        List<MedicalRecords> recordsByAddress = medicalRecordsDao.findByAddress(listByAddress);
        List<String> personsAgesInAddress = personsDao.findPersonsAges(recordsByAddress,listByAddress);
        //List<Persons> persons = personsDao.findAll();
        List<ChildAlert> childList = new ArrayList<>();
        List<MedicalRecords> foyer = new ArrayList<>();
        int i = 0;
        //TODO avoir la liste des personne en fonction d'une adresse
        //TODO recuperer le medical record de chaque personne
        //TODO faire la condition si la personne est mineur
        //TODO si mineur, recuperer tout les personne qui habite a cette adresse
        for (String age : personsAgesInAddress){
            if (Integer.parseInt(age) < 18){
                /*for (Persons person : listByAddress){
                    for (Persons habitant: persons){
                        if(Objects.equals(person.getAddress(),habitant.getAddress())){
                            foyer.add(habitant);
                        }
                    }
                }*/
                foyer.addAll(recordsByAddress);
                deleteDoublon(foyer);
                childList.add(new ChildAlert(listByAddress.get(i), age, foyer));
            }i++;
        }
        return  ResponseEntity.status(HttpStatus.OK).body(childList);
    }

}