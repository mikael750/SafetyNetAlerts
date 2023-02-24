package safetynet.alerts.DAO.Util;

import org.json.JSONObject;
import safetynet.alerts.DAO.FireStationsDaoImpl;
import safetynet.alerts.DAO.MedicalRecordsDaoImpl;
import safetynet.alerts.DAO.PersonsDaoImpl;

import java.io.FileWriter;
import java.io.PrintWriter;

public class tools {
    //TODO path dossier data.json
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

}
