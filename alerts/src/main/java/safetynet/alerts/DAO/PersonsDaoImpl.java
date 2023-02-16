package safetynet.alerts.DAO;

import safetynet.alerts.model.Persons;

import java.util.ArrayList;
import java.util.List;

public class PersonsDaoImpl implements PersonsDao{

    public static List<Persons> persons = new ArrayList<>();

    static{
        persons.add(new Persons(1, "John", "Boyd", "1509 Culver St", "Culver", "97451","841-874-6512", "jaboyd@email.com"));
        persons.add(new Persons(2, "Jacob", "Boyd", "1509 Culver St", "Culver", "97451","841-874-6513", "drk@email.com"));
        persons.add(new Persons(3, "Tenley", "Boyd", "1509 Culver St", "Culver", "97451","841-874-6512","tenz@email.com"));
        persons.add(new Persons(4, "Roger", "Boyd", "1509 Culver St", "Culver", "97451","841-874-6512", "jaboyd@email.com"));
        persons.add(new Persons(5, "Felicia", "Boyd", "1509 Culver St", "Culver", "97451","841-874-6544", "jaboyd@email.com"));
        persons.add(new Persons(6, "Jonanathan", "Marrack", "29 15th St", "Culver", "97451","841-874-6513", "drk@email.com"));
        persons.add(new Persons(7, "Tessa", "Carman", "834 Binoc Ave", "Culver", "97451","841-874-6512", "tenz@email.com"));
        persons.add(new Persons(8, "Peter", "Duncan", "834 Binoc Ave", "Culver", "97451","841-874-6512", "jaboyd@email.com"));
        persons.add(new Persons(9, "Foster", "Shepard", "748 Townings Dr", "Culver", "97451","841-874-6544", "jaboyd@email.com"));
        persons.add(new Persons(10, "Tony", "Cooper", "112 Steppes Pl", "Culver", "97451", "841-874-6874", "tcoop@ymail.com"));
        persons.add(new Persons(11, "Lily", "Cooper", "489 Manchester St", "Culver", "97451", "841-874-9845", "lily@email.com"));
        persons.add(new Persons(12, "Sophia", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7878", "soph@email.com"));
        persons.add(new Persons(13, "Warren", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512","ward@email.com"));
        persons.add(new Persons(14, "Zach", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", "zarc@email.com"));
        persons.add(new Persons(15, "Reginold", "Walker", "908 73rd St", "Culver", "97451", "841-874-8547", "reg@email.com"));
        persons.add(new Persons(16, "Jamie", "Peters", "908 73rd St", "Culver", "97451","841-874-7462", "jpeter@email.com"));
        persons.add(new Persons(17, "Ron", "Peters", "112 Steppes Pl", "Culver", "97451", "841-874-8888", "jpeter@email.com"));
        persons.add(new Persons(18, "Allison", "Boyd", "112 Steppes Pl", "Culver", "97451", "841-874-9888", "aly@imail.com"));
        persons.add(new Persons(19, "Brian", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com"));
        persons.add(new Persons(20, "Shawna", "Stelzer", "947 E. Rose Dr", "Culver", "97451","841-874-7784", "ssanw@email.com"));
        persons.add(new Persons(21, "Kendrik", "Stelzer", "947 E. Rose Dr", "Culver", "97451", "841-874-7784", "bstel@email.com"));
        persons.add(new Persons(22, "Clive", "Ferguson", "748 Townings Dr", "Culver", "97451", "841-874-6741", "clivfd@ymail.com"));
        persons.add(new Persons(23, "Eric", "Cadigan", "951 LoneTree Rd", "Culver", "97451", "841-874-7458", "gramps@email.com"));
    }

    @Override
    public List<Persons> findAll() {
        return persons;
    }

    @Override
    public Persons findById(int id) {
        for (Persons person : persons){
            if (person.getId() == id){
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
}