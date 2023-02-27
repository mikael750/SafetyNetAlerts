package safetynet.alerts.DAO;

import safetynet.alerts.model.Persons;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PersonsDao {
    List<Persons> findAll();
    Persons findById(String firstName, String lastName);
    Persons save(Persons persons);

    Persons update(Persons person);

    boolean delete(String firstName, String lastName);
}