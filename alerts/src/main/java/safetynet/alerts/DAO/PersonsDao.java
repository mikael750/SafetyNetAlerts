package safetynet.alerts.DAO;

import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;
import safetynet.alerts.model.response.AddressList;

import java.text.ParseException;
import java.util.List;

public interface PersonsDao {

    /**
     * Trouve toutes les personnes
     *
     * @return persons
     */
    List<Persons> findAll();

    /**
     * Trouve une personne par nom et prénom
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return person
     */
    Persons findById(String firstName, String lastName);


    /**
     * Trouves une liste des personnes par nom et prénom
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return listPersons
     */
    List<Persons> findByNames(String firstName, String lastName);

    /**
     * Trouves une liste d'ages d'après une liste de personnes
     *
     * @param findMedicalRecords findMedicalRecords
     * @param listPersons listPersons
     * @return ages
     * @throws ParseException calculateAge
     */
    List<String> findPersonsAges(List<MedicalRecords> findMedicalRecords, List<Persons> listPersons) throws ParseException;

    /**
     * Trouve par adresse une liste de personnes
     *
     * @param address address
     * @return personsByAddress
     */
    List<Persons> findByAddress(String address);

    /**
     * Trouve les personnes par leur ville
     *
     * @param city city
     * @return personsByCity
     */
    List<Persons> findByCity(String city);

    /**
     * Trouves les addresses des foyers
     *
     * @param stations stations
     * @param fireStationDao fireStationDao
     * @param medicalRecordsDao medicalRecordsDao
     * @return listAddressFoyer
     * @throws ParseException calculateAge
     */
    List<AddressList> findAddressFoyer(List<String> stations, FireStationsDao fireStationDao, MedicalRecordsDao medicalRecordsDao) throws ParseException;

    /**
     * Sauvegarde les changements de la base de donner des personnes
     *
     * @param person person
     * @return person
     */
    Persons save(Persons person);

    /**
     * Mis à Jour de la base de donner des personnes
     *
     * @param person person
     * @return person
     */
    Persons update(Persons person);

    /**
     * Supprime la personne de la base de donner
     *
     * @param firstName firstName
     * @param lastName lastName
     * @return boolean
     */
    boolean delete(String firstName, String lastName);
}