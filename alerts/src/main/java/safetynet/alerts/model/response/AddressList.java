package safetynet.alerts.model.response;

import lombok.Getter;
import lombok.Setter;
import safetynet.alerts.model.Persons;

import java.util.List;

@Getter
@Setter
public class AddressList {
    private String address;
    private List<EmergencyList> emergencyList;

    public AddressList(Persons person, List<EmergencyList> emergencyList){
        this.address = person.getAddress();
        this.emergencyList = emergencyList;
    }
}
