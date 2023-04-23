package safetynet.alerts.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import safetynet.alerts.DAO.FireStationsDao;
import safetynet.alerts.DAO.MedicalRecordsDao;
import safetynet.alerts.DAO.PersonsDao;
import safetynet.alerts.model.MedicalRecords;
import safetynet.alerts.model.Persons;
import safetynet.alerts.model.response.AddressList;
import safetynet.alerts.model.response.ChildAlert;
import safetynet.alerts.model.response.CountPersons;
import safetynet.alerts.model.response.EmergencyList;
import safetynet.alerts.model.response.InfoList;
import safetynet.alerts.model.response.SimplePerson;
import safetynet.alerts.util.AlertsUtils;

import static safetynet.alerts.util.AlertsUtils.deleteDoublon;

@RestController
public class SystemController {

    private static final Logger logger = LogManager.getLogger( SystemController.class);


    public SystemController(){}

    /**
     * Initialise la dataBase
     *
     * @throws IOException
     */
	@GetMapping(value = "/init")
	public static void initDataBase() throws IOException {
        AlertsUtils.initDataBase();
    }
}