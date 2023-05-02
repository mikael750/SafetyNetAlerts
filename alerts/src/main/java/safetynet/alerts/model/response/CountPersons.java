package safetynet.alerts.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CountPersons {

    private List<SimplePerson> persons = new ArrayList<>();
    private int adults;
    private int children;

    public CountPersons(){}

}
