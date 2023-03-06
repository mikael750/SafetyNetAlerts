package safetynet.alerts.DAO;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import safetynet.alerts.DAO.Util.tools;
import safetynet.alerts.model.FireStations;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class PersonsDaoImpl implements PersonsDao{

    public static List<Persons> persons = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(PersonsDaoImpl.class);


    public static void load(){
        logger.info("Chargement des donner des personnes.");
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
        logger.info("Recherche de toutes les personnes.");
        return persons;
    }

    @Override
    public Persons findById(String firstName, String lastName) {
        logger.info("Recherche d'une personne par nom et prenom.");
        for (Persons person : persons){
            if (Objects.equals(person.getFirstName(), firstName) && Objects.equals(person.getLastName(), lastName)){
                return person;
            }
        }
        return null;
    }

    @Override
    public List<Persons> findByFireStation(List<FireStations> listStations, List<Persons> listPersonsStations, String stationNumber, PersonsDao personsDao){
        logger.info("Recherche par caserne.");
        for (FireStations station : listStations){
            // si le numéro de station = stationNumber
            if(Integer.parseInt(station.getStation()) == Integer.parseInt(stationNumber)){
                listPersonsStations.addAll(personsDao.findByAddress(station.getAddress()));
            }
        }
        //enleve les doublon
        Set<Persons> set = new LinkedHashSet<>(listPersonsStations);
        listPersonsStations.clear();
        listPersonsStations.addAll(set);

        return listPersonsStations;
    }

    @Override
    public  String findPersonsAges(List<MedicalRecords> findMedicalRecords, List<Persons> listPersonsStations) throws ParseException {
        logger.info("Recherche par ages des personnes");
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        int mineurs = 0;
        int majeurs = 0;
        for (MedicalRecords records : findMedicalRecords){
            int i = 0;
            for (Persons person : listPersonsStations){
                if (i < listPersonsStations.size()){
                    if(Objects.equals(person.getFirstName(), records.getFirstName())) {//listPersonsStations.get(i)
                        long diffInMillies = Math.abs(date.getTime() - DateFor.parse(records.getBirthdate()).getTime());
                        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                        long years = diff / 365;
                        if (years < 18){mineurs++;}else{majeurs++;}
                    }
                }i++;
            }
        }
        //String minors = String.valueOf(mineurs);
        //String majors = String.valueOf(majeurs);
        return "Il y a " + mineurs + " mineurs sur " + majeurs + " majeurs";
    }

    @Override
    public List<Persons> findByAddress(String address){
        logger.info("Recherche par adresse");
        List<Persons> personsByAddress = new ArrayList<>();
        for (Persons person : persons){
            if (Objects.equals(person.getAddress(), address)){
                personsByAddress.add(person);
            }
        }
        return personsByAddress;
    }

    @Override
    public Persons save(Persons person) {
        logger.info("Sauvegarde des changements de la Dao des personnes");
        persons.add(person);
        tools.change();
        return person;
    }

    @Override
    public Persons update(Persons person) {
        logger.info("Mis à Jour de la Dao des personnes");
        persons.remove(person);
        persons.add(person);
        tools.change();
        return person;
    }


    @Override
    public boolean delete(String firstName, String lastName) {
        logger.info("Suppression des donnees d'une personne");
        boolean isDeleted = (persons.removeIf((persons -> Objects.equals(persons.getFirstName(), firstName) && Objects.equals(persons.getLastName(), lastName))));

        tools.change();
        return isDeleted;
    }


}