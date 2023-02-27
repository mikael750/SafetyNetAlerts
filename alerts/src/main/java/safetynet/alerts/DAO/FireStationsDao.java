package safetynet.alerts.DAO;

import safetynet.alerts.model.FireStations;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FireStationsDao {
    List<FireStations> findAll();
    FireStations findById(String address);
    FireStations save(FireStations fireStations);

    FireStations update(FireStations fireStation);

    boolean delete(String address);
}