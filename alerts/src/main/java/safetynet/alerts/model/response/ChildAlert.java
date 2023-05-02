package safetynet.alerts.model.response;

import lombok.Getter;
import lombok.Setter;
import safetynet.alerts.model.Persons;

import java.util.List;

@Getter
@Setter
public class ChildAlert {
    private String firstName;
    private String lastName;
    private int age;
    private List<Persons> foyer;

    public ChildAlert(Persons person, int age, List<Persons> foyer){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = age;
        this.foyer = foyer;
    }
}
