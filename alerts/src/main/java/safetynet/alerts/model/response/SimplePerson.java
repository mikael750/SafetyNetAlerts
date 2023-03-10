package safetynet.alerts.model.response;

import lombok.Getter;
import lombok.Setter;
import safetynet.alerts.model.Persons;

@Getter
@Setter
public class SimplePerson {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public SimplePerson(Persons person){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.phone = person.getPhone();
    }
}
