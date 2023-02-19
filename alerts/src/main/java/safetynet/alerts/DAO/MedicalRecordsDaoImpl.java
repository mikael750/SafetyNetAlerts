package safetynet.alerts.DAO;

import org.springframework.stereotype.Service;
import safetynet.alerts.model.MedicalRecords;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MedicalRecordsDaoImpl implements MedicalRecordsDao{

    public static List<MedicalRecords> medicalRecords = new ArrayList<>();

    static{
        medicalRecords.add(new MedicalRecords("John", "Boyd","03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"} ));
        medicalRecords.add(new MedicalRecords("Jacob", "Boyd", "03/06/1989", new String[]{"pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Tenley", "Boyd", "02/18/2012", new String[]{} , new String[]{"peanut"} ));
        medicalRecords.add(new MedicalRecords("Roger", "Boyd", "09/06/2017", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Felicia", "Boyd", "01/08/1986", new String[]{"tetracyclaz:650mg"} , new String[]{"xilliathal"} ));
        medicalRecords.add(new MedicalRecords("Jonanathan", "Marrack", "01/03/1989", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Tessa", "Carman", "02/18/2012", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Peter", "Duncan", "09/06/2000", new String[]{} , new String[]{"shellfish"} ));
        medicalRecords.add(new MedicalRecords("Foster", "Shepard", "01/08/1980", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Tony", "Cooper", "03/06/1994", new String[]{"hydrapermazol:300mg", "dodoxadin:30mg"} , new String[]{"shellfish"} ));
        medicalRecords.add(new MedicalRecords("Lily", "Cooper", "03/06/1994", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Sophia", "Zemicks", "03/06/1988", new String[]{"aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"} , new String[]{"peanut", "shellfish", "aznol"} ));
        medicalRecords.add(new MedicalRecords("Warren","Zemicks", "03/06/1985", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Zach", "Zemicks", "03/06/2017", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Reginold", "Walker", "08/30/1979", new String[]{"thradox:700mg"} , new String[]{"illisoxian"} ));
        medicalRecords.add(new MedicalRecords("Jamie", "Peters", "03/06/1982", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Ron", "Peters", "04/06/1965", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Allison", "Boyd", "03/15/1965", new String[]{"aznol:200mg"} , new String[]{"nillacilan"} ));
        medicalRecords.add(new MedicalRecords("Brian", "Stelzer", "12/06/1975", new String[]{"ibupurin:200mg", "hydrapermazol:400mg"} , new String[]{"nillacilan"} ));
        medicalRecords.add(new MedicalRecords("Shawna", "Stelzer", "07/08/1980", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Kendrik", "Stelzer", "03/06/2014", new String[]{"noxidian:100mg", "pharmacol:2500mg"} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Clive", "Ferguson", "03/06/1994", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords("Eric", "Cadigan", "08/06/1945", new String[]{"tradoxidine:400mg"} , new String[]{} ));
    }

    @Override
    public List<MedicalRecords> findAll() {
        return medicalRecords;
    }

    @Override
    public MedicalRecords findById(String firstName) {
        for (MedicalRecords medicalRecord : medicalRecords){
            if (Objects.equals(medicalRecord.getFirstName(), firstName)){
                return medicalRecord;
            }
        }
        return null;
    }

    @Override
    public MedicalRecords save(MedicalRecords medicalRecord) {
        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    @Override
    public boolean delete(String firstName) {

        boolean isDeleted = medicalRecords.removeIf(medicalRecords -> medicalRecords.getFirstName() == firstName);

        return isDeleted;
    }
}