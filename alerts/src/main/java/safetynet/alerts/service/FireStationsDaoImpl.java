package safetynet.alerts.service;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.DAO.PersonsDao;
import safetynet.alerts.util.AlertsUtils;
import safetynet.alerts.model.FireStations;
import safetynet.alerts.model.Persons;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static safetynet.alerts.util.AlertsUtils.deleteDoublon;

@Service
public class FireStationsDaoImpl implements FireStationsDao {

    public static List<FireStations> fireStations = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(FireStationsDaoImpl.class);

    /**
     * Charge les information de la database medicalrecords
     */
    public static void load(){
        logger.info("Chargement des donner des casernes.");
        try (InputStream file = FireStationsDaoImpl.class.getResourceAsStream("/saveData.json")){
            assert file != null;
            JsonIterator iter = JsonIterator.parse(file.readAllBytes());
            Any any = iter.readAny();
            Any fireStationsAny = any.get("firestations");
            fireStationsAny.forEach(a -> {
                fireStations.add(new FireStations(a.get("address").toString() , a.get("station").toString()));
            });
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public List<FireStations> findAll() {
        logger.info("Recherche de toutes les casernes.");
        return fireStations;
    }

    /**
     *{@inheritDoc}
     */
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

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Persons> findByNumberStation(String fireStationNumber, PersonsDao personsDao){
        logger.info("Recherche par station.");
        List<Persons> listPersonsFireStations = new ArrayList<>();
        for (FireStations station : fireStations) {
            // si le numéro de station = fireStationNumber
            if (Integer.parseInt(station.getStation()) == Integer.parseInt(fireStationNumber)) {
                listPersonsFireStations.addAll(personsDao.findByAddress(station.getAddress()));
            }
        }
        deleteDoublon(listPersonsFireStations);

        return listPersonsFireStations;
    }

    @Override
    public List<String> findAddressByStation(String stationNumber){
        logger.info("Recherche address par station.");
        List<String> listAddress = new ArrayList<>();
        for (FireStations station : fireStations) {
            if (Integer.parseInt(station.getStation()) == Integer.parseInt(stationNumber)) {
                listAddress.add(station.getAddress());
            }
        }
        deleteDoublon(listAddress);

        return listAddress;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public FireStations save(FireStations fireStation) {
        logger.info("Sauvegarde des changements de la Dao des casernes");
        fireStations.add(fireStation);
        AlertsUtils.writeJsonFile();
        return fireStation;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public FireStations update(FireStations fireStation) {
        logger.info("Mis à Jour de la Dao des casernes");
        fireStations.remove(fireStation);
        fireStations.add(fireStation);
        AlertsUtils.writeJsonFile();
        return fireStation;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean delete(String address) {
        logger.info("Suppression de l'adresse d'une caserne");
        boolean isDeleted = fireStations.removeIf(fireStations -> Objects.equals(fireStations.getAddress(), address));

        AlertsUtils.writeJsonFile();
        return isDeleted;
    }
}