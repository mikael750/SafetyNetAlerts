package safetynet.alerts.DAO;

import safetynet.alerts.model.FireStations;
import safetynet.alerts.model.Persons;

import java.util.List;

public interface FireStationsDao {

    /**
     * Trouve toutes les casernes
     *
     * @return fireStations
     */
    List<FireStations> findAll();

    /**
     * Trouve une caserne par l'adresse donner
     *
     * @param address address
     * @return fireStation
     */
    FireStations findById(String address);

    /**
     * Recherche les personnes par le numero de station
     *
     * @param fireStationNumber fireStationNumber
     * @param personsDao personsDao
     * @return listPersonsFireStations
     */
    List<Persons> findByNumberStation(String fireStationNumber, PersonsDao personsDao);

    /**
     * Recherche les personnes par l'adresse de la station
     *
     * @param stationNumber stationNumber
     * @return listAddress
     */
    List<String> findAddressByStation(String stationNumber);

    /**
     * Sauvegarde les changements de la base de donner des casernes
     *
     * @param fireStations fireStation
     * @return fireStation
     */
    FireStations save(FireStations fireStations);

    /**
     * Mis à Jour de la base de donner des casernes
     *
     * @param fireStation fireStation
     * @return fireStation
     */
    FireStations update(FireStations fireStation);

    /**
     * Supprime l'adresse d'une caserne
     *
     * @param address address
     * @return boolean
     */
    boolean delete(String address);
}