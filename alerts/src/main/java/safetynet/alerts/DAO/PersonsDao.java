package safetynet.alerts.DAO;

import safetynet.alerts.model.FireStations;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public interface PersonsDao {
    //TODO commentaire et inheritence {@inheritDoc}

    /**
     * @return
     */
    List<Persons> findAll();

    /**
     * @param firstName
     * @param lastName
     * @return
     */
    Persons findById(String firstName, String lastName);


    List<Persons> findByNames(String firstName, String lastName);

    /**
     * @param listStations
     * @param stationNumber
     * @param personsDao
     * @return
     */
    List<Persons> findByFireStation(List<FireStations> listStations, String stationNumber, PersonsDao personsDao);

    /**
     * @param findMedicalRecords
     * @param listPersons
     * @return
     * @throws ParseException
     */
    List<String> findPersonsAges(List<MedicalRecords> findMedicalRecords, List<Persons> listPersons) throws ParseException;

    /**
     * @param address
     * @return
     */
    List<Persons> findByAddress(String address);

    /**
     * @param persons
     * @return
     */
    Persons save(Persons persons);

    /**
     * @param person
     * @return
     */
    Persons update(Persons person);

    /**
     * @param firstName
     * @param lastName
     * @return
     */
    boolean delete(String firstName, String lastName);
}