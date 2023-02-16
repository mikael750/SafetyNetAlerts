package safetynet.alerts.model;

public class FireStations {

    private int id;
    private String address;
    private String station;

    public FireStations(){}

    public FireStations(int id, String address, String station){
        this.id = id;
        this.address = address;
        this.station = station;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation(){
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "firestations{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", station=" + station +
                '}';
    }
}
