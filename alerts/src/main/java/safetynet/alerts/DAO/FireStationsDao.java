package safetynet.alerts.DAO;

import safetynet.alerts.model.FireStations;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FireStationsDao {
    List<FireStations> findAll();
    FireStations findById(int id);
    FireStations save(FireStations fireStations);
}