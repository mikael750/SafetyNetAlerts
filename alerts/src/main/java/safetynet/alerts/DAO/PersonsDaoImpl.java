package safetynet.alerts.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.spi.Slice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import safetynet.alerts.AlertsApplication;
import safetynet.alerts.model.Persons;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class PersonsDaoImpl implements PersonsDao{

    public static List<Persons> persons = new ArrayList<>();
/*
    static{
        persons.add(new Persons("John", "Boyd", "1509 Culver St", "Culver", "97451","841-874-6512", "jaboyd@email.com"));
        persons.add(new Persons("Jacob", "Boyd", "1509 Culver St", "Culver", "97451","841-874-6513", "drk@email.com"));
        persons.add(new Persons("Tenley", "Boyd", "1509 Culver St", "Culver", "97451","841-874-6512","tenz@email.com"));
        persons.add(new Persons("Roger", "Boyd", "1509 Culver St", "Culver", "97451","841-874-6512", "jaboyd@email.com"));
        persons.add(new Persons("Felicia", "Boyd", "1509 Culver St", "Culver", "97451","841-874-6544", "jaboyd@email.com"));
        persons.add(new Persons("Jonanathan", "Marrack", "29 15th St", "Culver", "97451","841-874-6513", "drk@email.com"));
        persons.add(new Persons("Tessa", "Carman", "834 Binoc Ave", "Culver", "97451","841-874-6512", "tenz@email.com"));
        persons.add(new Persons("Peter", "Duncan", "834 Binoc Ave", "Culver", "97451","841-874-6512", "jaboyd@email.com"));
        persons.add(new Persons("Foster", "Shepard", "748 Townings Dr", "Culver", "97451","841-874-6544", "jaboyd@email.com"));
        persons.add(new Persons("Tony", "Cooper", "112 Steppes Pl", "Culver", "97451", "841-874-6874", "tcoop@ymail.com"));
        persons.add(new Persons("Lily", "Cooper", "489 Manchester St", "Culver", "97451", "841-874-9845", "lily@email.com"));
        persons.add(new Persons("Sophia", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7878", "soph@email.com"));
        persons.add(new Persons("Warren", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512","ward@email.com"));
        persons.add(new Persons("Zach", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", "zarc@email.com"));
        persons.add(new Persons("Reginold", "Walker", "908 73rd St", "Culver", "97451", "841-874-8547", "reg@email.com"));
        persons.add(new Persons("Jamie", "Peters", "908 73rd St", "Culver", "97451","841-874-7462", "jpeter@email.com"));
        persons.add(new Persons("Ron", "Peters", "112 Steppes Pl", "Culver", "97451", "841-874-8888", "jpeter@email.com"));
        persons.add(new Persons("Allison", "Boyd", "112 Steppes Pl", "Culver", "97451", "841-874-9888", "aly@imail.com"));
        persons.add(new Persons("Brian", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com"));
        persons.add(new Persons("Shawna", "Stelzer", "947 E. Rose Dr", "Culver", "97451","841-874-7784", "ssanw@email.com"));
        persons.add(new Persons("Kendrik", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com"));
        persons.add(new Persons("Clive", "Ferguson", "748 Townings Dr", "Culver", "97451", "841-874-6741", "clivfd@ymail.com"));
        persons.add(new Persons("Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451", "841-874-7458", "gramps@email.com"));
    }
*/

    public static void load(){
        try {
            InputStream file = PersonsDaoImpl.class.getResourceAsStream("/data.json");
            assert file != null;
            JsonIterator iter = JsonIterator.parse(file.readAllBytes());
            Any any = iter.readAny();
            Any personAny = any.get("persons");
            personAny.forEach(a -> {
                persons.add(new Persons(a.get("firstName").toString() , a.get("lastName").toString(), a.get("address").toString(), a.get("city").toString(), a.get("zip").toString(),a.get("phone").toString(), a.get("email").toString()));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }/**/

    @Override
    public List<Persons> findAll() {
        return persons;
    }

    @Override
    public Persons findById(String firstName) {
        for (Persons person : persons){
            if (Objects.equals(person.getFirstName(), firstName)){
                return person;
            }
        }
        return null;
    }

    @Override
    public Persons save(Persons person) {
        persons.add(person);
        return person;
    }

    @Override
    public boolean delete(String firstName) {

        boolean isDeleted = persons.removeIf(persons -> persons.getFirstName() == firstName);

        return isDeleted;
    }
}