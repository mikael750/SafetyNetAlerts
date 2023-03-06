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
    private String total;
//    private String minors;
//    private String adults;
//String minors, String adults
    public SimplePerson(Persons person, String total){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.phone = person.getPhone();
        this.total = total;
        //this.minors = minors;
        //this.adults = adults;
    }
}
