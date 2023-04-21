package safetynet.alerts.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.DAO.PersonsDao;
import safetynet.alerts.service.PersonsDaoImpl;
import safetynet.alerts.util.AlertsUtils;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import safetynet.alerts.model.response.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static safetynet.alerts.util.AlertsUtils.deleteDoublon;

@RestController
public class PersonsController {

    private static final Logger logger = LogManager.getLogger( PersonsController.class);


    private PersonsDao personsDao;
    private FireStationsDao fireStationDao;
    private MedicalRecordsDao medicalRecordsDao;

    public PersonsController(PersonsDao personsDao, FireStationsDao fireStationDao,  MedicalRecordsDao medicalRecordsDao){
        this.personsDao = personsDao;
        this.fireStationDao = fireStationDao;
        this.medicalRecordsDao = medicalRecordsDao;
    }

    /**
     * Initialise la dataBase
     *
     * @throws IOException
     */
    public static void getDataBase() throws IOException {
        AlertsUtils.initDataBase();
    }

    /**
     * affiche une liste des personnes dans la database
     *
     * @return List Persons
     */
    @GetMapping(value = "/person")
    public List<Persons> getPersons() {
        return personsDao.findAll();
    }

    /**
     * affiche la personne et ces donnee preciser dans l'URL
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return Persons
     */
    @GetMapping(value = "/person/{firstName}/{lastName}")
    public Persons showAPerson(@PathVariable String firstName, @PathVariable String lastName) {
        return personsDao.findById(firstName, lastName);
    }

    /**
     * ajoute une personne à la database Persons
     *
     * @param persons persons
     * @return personsAdded
     */
    @PostMapping(value = "/person")
    public ResponseEntity<Persons> addPersons(@RequestBody Persons persons) {
        var personsAdded = personsDao.save(persons);
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
        var updatePersons = personsDao.findById(firstName, lastName);
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

        var isDeleted = personsDao.delete(firstName, lastName);

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
    public ResponseEntity<CountPersons> getStationNumber(@RequestParam String stationNumber) throws ParseException {
        List<MedicalRecords> findMedicalRecords = medicalRecordsDao.findAll();
        List<Persons> listPersonsStations = fireStationDao.findByNumberStation(stationNumber,personsDao);
        List<String> ages = personsDao.findPersonsAges(findMedicalRecords,listPersonsStations);
        var children = 0;
        var adults = 0;
        for (String age : ages){
            if (Integer.parseInt(age) < 18){children++;}else{adults++;}
        }
        var result = new CountPersons();
        result.setPersons(listPersonsStations.stream().map((x)-> new SimplePerson(x)).toList());
        result.setAdults(adults);
        result.setChildren(children);
        return  ResponseEntity.ok(result);
    }

    /**
     * retourne une liste d'enfants habitant a cette adresse ainsi que les personnes habitant le foyer
     *
     * @param address address
     * @return childList
     * @throws ParseException calculateAge
     */
    @GetMapping(value = "/childAlert")
    public ResponseEntity<List<ChildAlert>> getChildList(@RequestParam String address) throws ParseException {
        var listByAddress = personsDao.findByAddress(address);
        List<ChildAlert> childList = new ArrayList<>();
        List<Persons> foyer = new ArrayList<>(listByAddress);
        for (Persons personne : listByAddress){
            var age = AlertsUtils.calculateAge(medicalRecordsDao.findById(personne.getFirstName(),personne.getLastName()));
            if (age < 18){
                foyer.remove(personne);
                childList.add(new ChildAlert(personne, age, foyer));
            }
        }
        return  ResponseEntity.ok(childList);
    }

    /**
     * Cette url retourne une liste des numéros de téléphone des résidents desservis par la caserne de
     * pompiers
     *
     * @param fireStation fireStation
     * @return listPhoneNumber
     */
    @GetMapping(value = "/phoneAlert")
    public ResponseEntity<List<Object>> getListPhoneNumber(@RequestParam String fireStation) {
        var listPersonsStations = fireStationDao.findByNumberStation(fireStation,personsDao);
        List<Object> listPhoneNumber = new ArrayList<>();
        for(Persons fireStations : listPersonsStations){
            listPhoneNumber.add(fireStations.getPhone());
        }
        deleteDoublon(listPhoneNumber);
        return ResponseEntity.ok(listPhoneNumber);
    }

    /**
     * Cette url retourne la liste des habitants vivant à l’adresse donnée ainsi que le numéro
     * de la caserne de pompiers la desservant
     *
     * @param address address
     * @return emergencyList
     * @throws ParseException calculateAge
     */
    @GetMapping(value = "/fire")
    public ResponseEntity<List<EmergencyList>> getListForFire(@RequestParam String address) throws ParseException {
        List<EmergencyList> emergencyList = new ArrayList<>();
        var listPersons = personsDao.findByAddress(address);

        for(Persons person : listPersons){
            var medicalRecord = medicalRecordsDao.findById(person.getFirstName(),person.getLastName());
            var age = AlertsUtils.calculateAge(medicalRecord);
            emergencyList.add(new EmergencyList(person, age, medicalRecord));
        }

        return ResponseEntity.ok(emergencyList);
    }

    /**
     * Cette url doit retourner une liste de tous les foyers desservis par la caserne, et les regroupe les
     * personnes par adresse.
     *
     * @param stations stations
     * @return listAddressFoyer
     * @throws ParseException calculateAge
     */
    @GetMapping(value = "/flood/stations")
    public ResponseEntity<List<AddressList>> getListForFlood(@RequestParam List<String> stations) throws ParseException {
        logger.info( "Size"+stations.size() );
        System.out.println("Size"+stations.size());
        return ResponseEntity.ok(personsDao.findAddressFoyer(stations,fireStationDao,medicalRecordsDao));
    }

    /**
     * Cette url retourne le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
     * posologie, allergies) de chaque habitant préciser.
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return infoList
     * @throws ParseException calculateAge
     */
    @GetMapping(value = "/personInfo")
    public ResponseEntity<List<InfoList>> getInfoOnPerson(@RequestParam String firstName,@RequestParam String lastName) throws ParseException {
        List<InfoList> infoList = new ArrayList<>();
        var listPersons = personsDao.findByNames(firstName,lastName);

        for(Persons person : listPersons){
            var medicalRecord = medicalRecordsDao.findById(person.getFirstName(),person.getLastName());
            var age = AlertsUtils.calculateAge(medicalRecord);
            infoList.add(new InfoList(person, age, medicalRecord));
        }

        return ResponseEntity.ok(infoList);
    }

    /**
     * Cette url doit retourner les adresses mail de tous les habitants de la ville.
     *
     * @param city city
     * @return listEmail
     */
    @GetMapping(value = "/communityEmail")
    public ResponseEntity<List<String>> getAllMailOfCity(@RequestParam String city){
        List<String> listEmail = new ArrayList<>();
        var listPerson = personsDao.findByCity(city);
        for (Persons email : listPerson){
            listEmail.add(email.getEmail());
        }
        return ResponseEntity.ok(listEmail);
    }

}