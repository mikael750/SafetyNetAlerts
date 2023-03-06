package safetynet.alerts.DAO;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import safetynet.alerts.DAO.Util.tools;
import safetynet.alerts.model.FireStations;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FireStationsDaoImpl implements FireStationsDao{

    public static List<FireStations> fireStations = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(FireStationsDaoImpl.class);

    public static void load(){
        logger.info("Chargement des donner des casernes.");
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
        logger.info("Recherche de toutes les casernes.");
        return fireStations;
    }

    @Override
    public FireStations findById(String address) {
        logger.info("Recherche d'une caserne par address.");
        for (FireStations fireStation : fireStations){
            if (Objects.equals(fireStation.getAddress(), address)){
                return fireStation;
            }
        }
        return null;
    }

    @Override
    public FireStations save(FireStations fireStation) {
        logger.info("Sauvegarde des changements de la Dao des casernes");
        fireStations.add(fireStation);
        tools.change();
        return fireStation;
    }

    @Override
    public FireStations update(FireStations fireStation) {
        logger.info("Mis à Jour de la Dao des casernes");
        fireStations.remove(fireStation);
        fireStations.add(fireStation);
        tools.change();
        return fireStation;
    }

    @Override
    public boolean delete(String address) {
        logger.info("Suppression de l'adress d'une casernes");
        boolean isDeleted = fireStations.removeIf(fireStations -> Objects.equals(fireStations.getAddress(), address));

        tools.change();
        return isDeleted;
    }
}
