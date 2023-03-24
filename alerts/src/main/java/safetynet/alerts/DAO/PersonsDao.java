package safetynet.alerts.DAO;

import safetynet.alerts.model.FireStations;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public interface PersonsDao {
    List<Persons> findAll();
    Persons findById(String firstName, String lastName);

//TODO commentaire et inheritence
    /**
     * @param listStations
     * @param stationNumber
     * @param personsDao
     * @return
     */
    List<Persons> findByFireStation(List<FireStations> listStations, String stationNumber, PersonsDao personsDao);

    List<String> findPersonsAges(List<MedicalRecords> findMedicalRecords, List<Persons> listPersons) throws ParseException;

    List<Persons> findByAddress(String address);

    Persons save(Persons persons);

    Persons update(Persons person);

    boolean delete(String firstName, String lastName);
}