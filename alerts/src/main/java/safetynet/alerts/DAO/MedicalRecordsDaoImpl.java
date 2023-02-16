package safetynet.alerts.DAO;

import org.springframework.stereotype.Service;
import safetynet.alerts.model.MedicalRecords;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalRecordsDaoImpl implements MedicalRecordsDao{

    public static List<MedicalRecords> medicalRecords = new ArrayList<>();

    static{
        medicalRecords.add(new MedicalRecords(1, "John", "Boyd","03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"} ));
        medicalRecords.add(new MedicalRecords(2, "Jacob", "Boyd", "03/06/1989", new String[]{"pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"} , new String[]{""} ));
        medicalRecords.add(new MedicalRecords(3, "Tenley", "Boyd", "02/18/2012", new String[]{""} , new String[]{"peanut"} ));
        medicalRecords.add(new MedicalRecords(4, "Roger", "Boyd", "09/06/2017", new String[]{""} , new String[]{""} ));
        medicalRecords.add(new MedicalRecords(5, "Felicia", "Boyd", "01/08/1986", new String[]{"tetracyclaz:650mg"} , new String[]{"xilliathal"} ));
        medicalRecords.add(new MedicalRecords(6, "Jonanathan", "Marrack", "01/03/1989", new String[]{""} , new String[]{""} ));
        medicalRecords.add(new MedicalRecords(7, "Tessa", "Carman", "02/18/2012", new String[]{""} , new String[]{""} ));
        medicalRecords.add(new MedicalRecords(8, "Peter", "Duncan", "09/06/2000", new String[]{""} , new String[]{"shellfish"} ));
        medicalRecords.add(new MedicalRecords(9, "Foster", "Shepard", "01/08/1980", new String[]{""} , new String[]{""} ));
        medicalRecords.add(new MedicalRecords(10, "Tony", "Cooper", "03/06/1994", new String[]{"hydrapermazol:300mg", "dodoxadin:30mg"} , new String[]{"shellfish"} ));
        medicalRecords.add(new MedicalRecords(11, "Lily", "Cooper", "03/06/1994", new String[]{""} , new String[]{""} ));
        medicalRecords.add(new MedicalRecords(12, "Sophia", "Zemicks", "03/06/1988", new String[]{"aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"} , new String[]{"peanut", "shellfish", "aznol"} ));
        medicalRecords.add(new MedicalRecords(13, "Warren","Zemicks", "03/06/1985", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords(14, "Zach", "Zemicks", "03/06/2017", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords(15, "Reginold", "Walker", "08/30/1979", new String[]{"thradox:700mg"} , new String[]{"illisoxian"} ));
        medicalRecords.add(new MedicalRecords(16, "Jamie", "Peters", "03/06/1982", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords(17, "Ron", "Peters", "04/06/1965", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords(18, "Allison", "Boyd", "03/15/1965", new String[]{"aznol:200mg"} , new String[]{"nillacilan"} ));
        medicalRecords.add(new MedicalRecords(19, "Brian", "Stelzer", "12/06/1975", new String[]{"ibupurin:200mg", "hydrapermazol:400mg"} , new String[]{"nillacilan"} ));
        medicalRecords.add(new MedicalRecords(20, "Shawna", "Stelzer", "07/08/1980", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords(21, "Kendrik", "Stelzer", "03/06/2014", new String[]{"noxidian:100mg", "pharmacol:2500mg"} , new String[]{} ));
        medicalRecords.add(new MedicalRecords(22, "Clive", "Ferguson", "03/06/1994", new String[]{} , new String[]{} ));
        medicalRecords.add(new MedicalRecords(23, "Eric", "Cadigan", "08/06/1945", new String[]{"tradoxidine:400mg"} , new String[]{} ));
    }

    @Override
    public List<MedicalRecords> findAll() {
        return medicalRecords;
    }

    @Override
    public MedicalRecords findById(int id) {
        for (MedicalRecords medicalRecord : medicalRecords){
            if (medicalRecord.getId() == id){
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
}