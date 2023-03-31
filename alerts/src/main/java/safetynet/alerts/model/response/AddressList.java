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

    public AddressList(String address, List<EmergencyList> emergencyList){
        this.address = address;
        this.emergencyList = emergencyList;
    }
}
