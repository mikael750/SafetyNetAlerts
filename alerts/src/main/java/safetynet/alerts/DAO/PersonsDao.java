package safetynet.alerts.DAO;

import safetynet.alerts.model.FireStations;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;


public interface PersonsDao {
    List<Persons> findAll();
    Persons findById(String firstName, String lastName);

    List<Persons> findByFireStation(List<FireStations> listStations, List<Persons> listPersonsStations, String stationNumber, PersonsDao personsDao);

    String findPersonsAges(List<MedicalRecords> findMedicalRecords, List<Persons> listPersonsStations) throws ParseException;

    List<Persons> findByAddress(String address);

    Persons save(Persons persons);

    Persons update(Persons person);

    boolean delete(String firstName, String lastName);
}