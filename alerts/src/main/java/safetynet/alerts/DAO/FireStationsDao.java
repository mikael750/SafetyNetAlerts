package safetynet.alerts.DAO;

import safetynet.alerts.model.FireStations;
import org.springframework.stereotype.Repository;
import safetynet.alerts.model.Persons;

import java.util.List;

public interface FireStationsDao {

    /**
     * @return
     */
    List<FireStations> findAll();

    /**
     * @param address
     * @return
     */
    FireStations findById(String address);

    /**
     * @param firestationNumber
     * @param personsDao
     * @return
     */
    List<Persons> findByNumberStation(String firestationNumber, PersonsDao personsDao);

    /**
     * @param stationNumber
     * @return
     */
    List<String> findAddressByStation(String stationNumber);

    /**
     * @param fireStations
     * @return
     */
    FireStations save(FireStations fireStations);

    /**
     * @param fireStation
     * @return
     */
    FireStations update(FireStations fireStation);

    /**
     * @param address
     * @return
     */
    boolean delete(String address);
}