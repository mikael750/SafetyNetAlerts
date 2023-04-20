package safetynet.alerts.service;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.DAO.PersonsDao;
import safetynet.alerts.util.AlertsUtils;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;
import safetynet.alerts.model.response.AddressList;
import safetynet.alerts.model.response.EmergencyList;

import java.io.*;
import java.text.ParseException;
import java.util.*;

import static safetynet.alerts.util.AlertsUtils.calculateAge;

@Service
public class PersonsDaoImpl implements PersonsDao {

    public static List<Persons> persons = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(PersonsDaoImpl.class);

    /**
     * Charge les informations de la database persons
     */
    public static void load(){
        logger.info("Chargement des donner des personnes.");
        try (InputStream file = PersonsDaoImpl.class.getResourceAsStream("/saveData.json")){
            assert file != null;
            JsonIterator iter = JsonIterator.parse(file.readAllBytes());
            Any any = iter.readAny();
            Any personAny = any.get("persons");
            personAny.forEach(a -> {
                persons.add(new Persons(a.get("firstName").toString() , a.get("lastName").toString(), a.get("address").toString(), a.get("city").toString(), a.get("zip").toString(),a.get("phone").toString(), a.get("email").toString()));
            });
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Persons> findAll() {
        logger.info("Recherche de toutes les personnes.");
        return persons;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Persons findById(String firstName, String lastName) {
        logger.info("Recherche d'une personne par nom et prenom.");
        for (Persons person : persons){
            if (Objects.equals(person.getFirstName(), firstName) && Objects.equals(person.getLastName(), lastName)){
                return person;
            }
        }
        return null;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Persons> findByNames(String firstName, String lastName) {
        logger.info("Recherche des personnes par nom et prenom.");
        List<Persons> listPersons = new ArrayList<>();
        for (Persons person : persons){
            if (Objects.equals(person.getFirstName(), firstName) && Objects.equals(person.getLastName(), lastName)){
                listPersons.add(person);
            }
        }
        return listPersons;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<String> findPersonsAges(List<MedicalRecords> findMedicalRecords, List<Persons> listPersons) throws ParseException {
        logger.info("Recherche par ages des personnes");
        List<String> ages = new ArrayList<>();
        for (MedicalRecords records : findMedicalRecords){
            int i = 0;
            for (Persons person : listPersons){
                if (i < listPersons.size()){
                    if(Objects.equals(person.getFirstName(), records.getFirstName()) && Objects.equals(person.getLastName(), records.getLastName())) {
                        ages.add(String.valueOf(calculateAge(records)));
                    }
                }i++;
            }
        }
        return ages;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Persons> findByAddress(String address){
        logger.info("Recherche par adresse");
        List<Persons> personsByAddress = new ArrayList<>();
        for (Persons person : persons){
            if (Objects.equals(person.getAddress(), address)){
                personsByAddress.add(person);
            }
        }
        return personsByAddress;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Persons> findByCity(String city){
        logger.info("Recherche par ville");
        List<Persons> personsByCity = new ArrayList<>();
        for (Persons person : persons){
            if (Objects.equals(person.getCity(), city)){
                personsByCity.add(person);
            }
        }
        return personsByCity;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<AddressList> findAddressFoyer(List<String> stations, FireStationsDao fireStationDao, MedicalRecordsDao medicalRecordsDao) throws ParseException {
        logger.info("Recherche des addresses des foyers");
        List<AddressList> listAddressFoyer = new ArrayList<>();
        List<Persons> listPersons;
        List<String> peopleAddress;
        for (String stationNumber : stations){
            peopleAddress = fireStationDao.findAddressByStation(stationNumber);
            for (String address : peopleAddress){
                listPersons = findByAddress(address);
                List<EmergencyList> emergencyList = new ArrayList<>();
                for(Persons person : listPersons){
                    MedicalRecords medicalRecord = medicalRecordsDao.findById(person.getFirstName(),person.getLastName());
                    int age = AlertsUtils.calculateAge(medicalRecord);
                    emergencyList.add(new EmergencyList(person, age, medicalRecord));
                }
                listAddressFoyer.add((new AddressList(address,emergencyList)));
            }
        }

        return listAddressFoyer;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Persons save(Persons person) {
        logger.info("Sauvegarde des changements de la Dao des personnes");
        persons.add(person);
        AlertsUtils.writeJsonFile();
        return person;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Persons update(Persons person) {
        logger.info("Mis à Jour de la Dao des personnes");
        persons.remove(person);
        persons.add(person);
        AlertsUtils.writeJsonFile();
        return person;
    }


    /**
     *{@inheritDoc}
     */
    @Override
    public boolean delete(String firstName, String lastName) {
        logger.info("Suppression des donnees d'une personne");
        boolean isDeleted = (persons.removeIf((persons -> Objects.equals(persons.getFirstName(), firstName) && Objects.equals(persons.getLastName(), lastName))));

        AlertsUtils.writeJsonFile();
        return isDeleted;
    }


}