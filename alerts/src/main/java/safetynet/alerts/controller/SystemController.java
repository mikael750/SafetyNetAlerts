package safetynet.alerts.controller;

import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import safetynet.alerts.util.AlertsUtils;

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
        logger.info("Initialisation de la dataBase");
        AlertsUtils.initDataBase();
    }
}
