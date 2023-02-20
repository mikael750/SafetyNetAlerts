package safetynet.alerts.DAO;

import safetynet.alerts.model.Persons;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PersonsDao {
    List<Persons> findAll();
    Persons findById(String firstName);
    Persons save(Persons persons);
    boolean delete(String firstName);
}