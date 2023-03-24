package safetynet.alerts.model.response;

import lombok.Getter;
import lombok.Setter;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;

import java.util.List;

@Getter
@Setter
public class EmergencyList {
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public EmergencyList(Persons person, int age, MedicalRecords medicalRecords){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.age = age;
        this.medications = medicalRecords.getMedications();
        this.allergies = medicalRecords.getAllergies();
    }
}
