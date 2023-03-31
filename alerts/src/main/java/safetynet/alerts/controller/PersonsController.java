package safetynet.alerts.controller;

import org.springframework.http.HttpStatus;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.DAO.PersonsDao;
import safetynet.alerts.DAO.Util.tools;
import safetynet.alerts.model.FireStations;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

import safetynet.alerts.model.response.*;

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
        List<MedicalRecords> findMedicalRecords = medicalRecordsDao.findAll();
        List<Persons> listPersonsStations = fireStationDao.findByNumberStation(stationNumber,personsDao);
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
        List<Persons> listByAddress = personsDao.findByAddress(address);
        List<ChildAlert> childList = new ArrayList<>();
        List<Persons> foyer = new ArrayList<>(listByAddress);
        for (Persons personne : listByAddress){
            int age = tools.calculateAge(medicalRecordsDao.findById(personne.getFirstName(),personne.getLastName()));
            if (age < 18){
                foyer.remove(personne);
                childList.add(new ChildAlert(personne, age, foyer));
            }
        }
        return  ResponseEntity.status(HttpStatus.OK).body(childList);
    }

    /**
     * Cette url retourne une liste des numéros de téléphone des résidents desservis par la caserne de
     * pompiers
     *
     * @param firestation firestation
     * @return listPhoneNumber
     */
    @GetMapping(value = "/phoneAlert")
    public ResponseEntity getListPhoneNumber(@RequestParam String firestation) {
        List<Persons> listPersonsStations = fireStationDao.findByNumberStation(firestation,personsDao);
        List<Object> listPhoneNumber = new ArrayList<>();
        for(Persons fireStations : listPersonsStations){
            listPhoneNumber.add(fireStations.getPhone());
        }
        deleteDoublon(listPhoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body(listPhoneNumber);
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
    public ResponseEntity getListForFire(@RequestParam String address) throws ParseException {
        List<EmergencyList> emergencyList = new ArrayList<>();
        List<Persons> listPersons = personsDao.findByAddress(address);

        for(Persons person : listPersons){
            MedicalRecords medicalRecord = medicalRecordsDao.findById(person.getFirstName(),person.getLastName());
            int age = tools.calculateAge(medicalRecord);
            emergencyList.add(new EmergencyList(person, age, medicalRecord));
        }

        return ResponseEntity.status(HttpStatus.OK).body(emergencyList);
    }

    /**
     * @param stations stations
     * @return listAddressFoyer
     * @throws ParseException calculateAge
     */
    /*
    http://localhost:8080/flood/stations?stations=<a list of station_numbers>
Cette url doit retourner une liste de tous les foyers desservis par la caserne, et les regroupe les
personnes par adresse. Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
faire figurer leurs antécédents médicaux (médicaments, posologie et allergies) à côté de chaque nom.
     */
    @GetMapping(value = "/flood/stations")
    public ResponseEntity getListForFlood(@RequestParam List<String> stations) throws ParseException {
        List<EmergencyList> emergencyList = new ArrayList<>();
        List<Persons> listPersons = new ArrayList<>();
        List<AddressList> listAddressFoyer = new ArrayList<>();
        List<String> peopleAddress = new ArrayList<>();

        for (String stationNumber : stations){
            listPersons.addAll(fireStationDao.findByNumberStation(stationNumber,personsDao));
        }
        /*for(Persons person : listPersons){
            peopleAddress.add(person.getAddress());
        }*/

        deleteDoublon(peopleAddress);
        for(Persons person : listPersons){
        //for(String a : peopleAddress){
            MedicalRecords medicalRecord = medicalRecordsDao.findById(person.getFirstName(),person.getLastName());
            int age = tools.calculateAge(medicalRecord);
            emergencyList.add(new EmergencyList(person, age, medicalRecord));
            listAddressFoyer.add(new AddressList(person, emergencyList));
        }//}
        deleteDoublon(listAddressFoyer);

        return ResponseEntity.status(HttpStatus.OK).body(listAddressFoyer);
    }

    /**
     * Cette retourne le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
     * posologie, allergies) de chaque habitant préciser.
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return infoList
     * @throws ParseException calculateAge
     */
    @GetMapping(value = "/personInfo")
    public ResponseEntity getInfoOnPerson(@RequestParam String firstName,@RequestParam String lastName) throws ParseException {
        List<InfoList> infoList = new ArrayList<>();
        List<Persons> listPersons = personsDao.findByNames(firstName,lastName);

        for(Persons person : listPersons){
            MedicalRecords medicalRecord = medicalRecordsDao.findById(person.getFirstName(),person.getLastName());
            int age = tools.calculateAge(medicalRecord);
            infoList.add(new InfoList(person, age, medicalRecord));
        }

        return ResponseEntity.status(HttpStatus.OK).body(infoList);
    }

    /**
     * Cette url doit retourner les adresses mail de tous les habitants de la ville.
     *
     * @param city city
     * @return listEmail
     */
    @GetMapping(value = "/communityEmail")
    public ResponseEntity getAllMailOfCity(@RequestParam String city){
        List<String> listEmail = new ArrayList<>();
        List<Persons> listPerson = personsDao.findByCity(city);
        for (Persons email : listPerson){
            listEmail.add(email.getEmail());
        }
        return ResponseEntity.status(HttpStatus.OK).body(listEmail);
    }

}