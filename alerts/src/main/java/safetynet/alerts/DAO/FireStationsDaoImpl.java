package safetynet.alerts.DAO;

import org.springframework.stereotype.Service;
import safetynet.alerts.model.FireStations;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationsDaoImpl implements FireStationsDao{

    public static List<FireStations> fireStations = new ArrayList<>();

    static{
        fireStations.add(new FireStations(1, "1509 Culver St", "3"));
        fireStations.add(new FireStations(2, "29 15th St","2"));
        fireStations.add(new FireStations(3, "834 Binoc Ave", "3"));
        fireStations.add(new FireStations(4, "644 Gershwin Cir", "1"));
        fireStations.add(new FireStations(5, "748 Townings Dr", "3"));
        fireStations.add(new FireStations(6, "112 Steppes Pl", "3"));
        fireStations.add(new FireStations(7, "489 Manchester St", "4"));
        fireStations.add(new FireStations(8, "892 Downing Ct", "2"));
        fireStations.add(new FireStations(9, "908 73rd St", "1"));
        fireStations.add(new FireStations(10, "112 Steppes Pl", "4"));
        fireStations.add(new FireStations(11, "947 E. Rose Dr", "1"));
        fireStations.add(new FireStations(12, "748 Townings Dr", "3"));
        fireStations.add(new FireStations(13, "951 LoneTree Rd", "2"));
    }

    @Override
    public List<FireStations> findAll() {
        return fireStations;
    }

    @Override
    public FireStations findById(int id) {
        for (FireStations fireStation : fireStations){
            if (fireStation.getId() == id){
                return fireStation;
            }
        }
        return null;
    }

    @Override
    public FireStations save(FireStations fireStation) {
        fireStations.add(fireStation);
        return fireStation;
    }
}
