package safetynet.alerts.model.response;

import lombok.Getter;
import lombok.Setter;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;

import java.util.List;

@Getter
@Setter
public class ChildAlert {
    private String firstName;
    private String lastName;
    private String age;
    private List<MedicalRecords> foyer;

    public ChildAlert(Persons person, String age, List<MedicalRecords> foyer){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = age;
        this.foyer = foyer;

    }
}
