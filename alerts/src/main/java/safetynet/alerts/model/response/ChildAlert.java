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
    private String age;
    private List<Persons> foyer;
//La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
//membres du foyer.

    public ChildAlert(Persons person, String age, List<Persons> foyer){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = age;
        this.foyer = foyer;

    }
}
