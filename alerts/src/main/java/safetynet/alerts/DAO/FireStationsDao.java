package safetynet.alerts.DAO;

import safetynet.alerts.model.FireStations;
import org.springframework.stereotype.Repository;
import safetynet.alerts.model.Persons;

import java.util.List;

public interface FireStationsDao {
    List<FireStations> findAll();
    FireStations findById(String address);

    List<Persons> findByNumberStation(String firestationNumber, PersonsDao personsDao);

    FireStations save(FireStations fireStations);

    FireStations update(FireStations fireStation);

    boolean delete(String address);
}