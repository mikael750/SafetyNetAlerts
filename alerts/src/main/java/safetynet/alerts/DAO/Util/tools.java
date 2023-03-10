package safetynet.alerts.DAO.Util;

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

public class tools {
    public static void change() {
        JSONObject Objet = new JSONObject();
        Objet.put("persons",PersonsDaoImpl.persons);
        Objet.put("medicalrecords",MedicalRecordsDaoImpl.medicalRecords);
        Objet.put("firestations",FireStationsDaoImpl.fireStations);

        try (PrintWriter out = new PrintWriter(new FileWriter("alerts/target/classes/data.json"))) {
            out.write(Objet.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int calculateAge(MedicalRecords records) throws ParseException {
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
        long diffInMillies = Math.abs(date.getTime() - DateFor.parse(records.getBirthdate()).getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        long years = diff / 365;
        return (int) years;
    }

    public static List deleteDoublon(List list){
        Set set = new LinkedHashSet<>(list);
        list.clear();
        list.addAll(set);
        return list;
    }
}
