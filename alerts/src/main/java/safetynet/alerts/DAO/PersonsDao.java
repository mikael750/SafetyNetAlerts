package safetynet.alerts.DAO;

import safetynet.alerts.model.Persons;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PersonsDao {
    List<Persons> findAll();
    Persons findById(int id);
    Persons save(Persons persons);
    boolean delete(int id);
}