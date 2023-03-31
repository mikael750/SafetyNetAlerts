package safetynet.alerts.model.response;

import lombok.Getter;
import lombok.Setter;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;

import java.util.List;

@Getter
@Setter
public class InfoList {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private List<String> medications;
    private List<String> allergies;

    public InfoList(Persons person, int age, MedicalRecords medicalRecords){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.age = age;
        this.email = person.getEmail();
        this.medications = medicalRecords.getMedications();
        this.allergies = medicalRecords.getAllergies();
    }


}
