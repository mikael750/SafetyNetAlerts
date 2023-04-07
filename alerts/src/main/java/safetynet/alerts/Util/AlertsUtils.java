package safetynet.alerts.Util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import safetynet.alerts.DAO.FireStationsDaoImpl;
import safetynet.alerts.DAO.MedicalRecordsDaoImpl;
import safetynet.alerts.DAO.PersonsDaoImpl;
import safetynet.alerts.model.MedicalRecords;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class AlertsUtils {

    private final static String FILE_PATH = "alerts/src/main/resources/saveData";

    private static final Logger logger = LogManager.getLogger(MedicalRecordsDaoImpl.class);

    /**
     * assure la modification de la base de donner en sortie
     *
     * @return boolean
     */
    public static boolean writeJsonFile() {
        var Objet = new JSONObject();
        Objet.put("persons",PersonsDaoImpl.persons);
        Objet.put("medicalrecords",MedicalRecordsDaoImpl.medicalRecords);
        Objet.put("firestations",FireStationsDaoImpl.fireStations);

        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
            out.write(Objet.toString());
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    /**
     * Calcule l'age d'une personne selon les record medical donner
     *
     * @param records records
     * @return years
     * @throws ParseException DateFor
     */
    public static int calculateAge(MedicalRecords records) throws ParseException {
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
        long diffInMillies = Math.abs(date.getTime() - DateFor.parse(records.getBirthdate()).getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        long years = diff / 365;
        return (int) years;
    }

    /**
     * Supprime les doublons dans une liste
     *
     * @param list list
     * @return list
     */
    public static List deleteDoublon(List list){
        Set set = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(set);
        return list;
    }
}