package safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FireStations {
    private String address;
    private String station;

    public FireStations(){}

    public FireStations(String address, String station){
        this.address = address;
        this.station = station;
    }

    @Override
    public String toString() {
        return "firestations{" +
                "address='" + address +
                ", station=" + station +
                '}';
    }
}
