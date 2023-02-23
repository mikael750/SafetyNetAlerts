package safetynet.alerts.DAO;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.springframework.stereotype.Service;
import safetynet.alerts.model.FireStations;
import safetynet.alerts.model.Persons;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FireStationsDaoImpl implements FireStationsDao{

    public static List<FireStations> fireStations = new ArrayList<>();

    /*static{
        fireStations.add(new FireStations("1509 Culver St", "3"));
        fireStations.add(new FireStations("29 15th St","2"));
        fireStations.add(new FireStations("834 Binoc Ave", "3"));
        fireStations.add(new FireStations("644 Gershwin Cir", "1"));
        fireStations.add(new FireStations("748 Townings Dr", "3"));
        fireStations.add(new FireStations("112 Steppes Pl", "3"));
        fireStations.add(new FireStations("489 Manchester St", "4"));
        fireStations.add(new FireStations("892 Downing Ct", "2"));
        fireStations.add(new FireStations("908 73rd St", "1"));
        fireStations.add(new FireStations("112 Steppes Pl", "4"));
        fireStations.add(new FireStations("947 E. Rose Dr", "1"));
        fireStations.add(new FireStations("748 Townings Dr", "3"));
        fireStations.add(new FireStations("951 LoneTree Rd", "2"));
    }*/

    public static void load(){
        try {
            InputStream file = FireStationsDaoImpl.class.getResourceAsStream("/data.json");
            assert file != null;
            JsonIterator iter = JsonIterator.parse(file.readAllBytes());
            Any any = iter.readAny();
            Any fireStationsAny = any.get("firestations");
            fireStationsAny.forEach(a -> {
                fireStations.add(new FireStations(a.get("address").toString() , a.get("station").toString()));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FireStations> findAll() {
        return fireStations;
    }

    @Override
    public FireStations findById(String address) {
        for (FireStations fireStation : fireStations){
            if (Objects.equals(fireStation.getAddress(), address)){
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

    @Override
    public boolean delete(String address) {

        boolean isDeleted = fireStations.removeIf(fireStations -> Objects.equals(fireStations.getAddress(), address));

        return isDeleted;
    }
}
