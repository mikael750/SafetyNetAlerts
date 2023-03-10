package safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MedicalRecords {
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

    public MedicalRecords(){}

    public MedicalRecords(String firstName, String lastName, String birthdate, List<String> medications, List<String> allergies){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    @Override
    public String toString() {
        return "medicalrecords{" +
                "firstName='" + firstName +
                ", lastName='" + lastName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", medications='" + medications + '\'' +
                ", allergies=" + allergies +
                '}';
    }
}
