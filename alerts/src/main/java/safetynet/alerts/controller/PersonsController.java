package safetynet.alerts.controller;

import org.springframework.http.HttpStatus;
import safetynet.alerts.DAO.PersonsDao;
import safetynet.alerts.model.Persons;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class PersonsController {

    private final PersonsDao personsDao;

    public PersonsController(PersonsDao personsDao){
        this.personsDao = personsDao;
    }

    @GetMapping(value = "/persons")
    public List<Persons> listePersons() {
        return personsDao.findAll();
    }

    @GetMapping(value = "/person/{firstName}/{lastName}")
    public Persons afficherUnePersonne(@PathVariable String firstName, @PathVariable String lastName) {
        return personsDao.findById(firstName, lastName);
    }

    @PostMapping(value = "/person")
    public ResponseEntity<Persons> ajouterPersons(@RequestBody Persons persons) {
        Persons personsAdded = personsDao.save(persons);
        if (Objects.isNull(personsAdded)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(personsAdded.getFirstName(),personsAdded.getLastName())//, lastName
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Persons> updatePersons(@PathVariable String firstName, @PathVariable String lastName,@RequestBody Persons personsDetails) throws Exception {
        Persons updatePersons = personsDao.findById(firstName, lastName);
        if (Objects.isNull(updatePersons)){
            throw new Exception(firstName + " n'est pas inscrit");
        }
        //updatePersons.setFirstName(personsDetails.getFirstName());
        //updatePersons.setLastName(personsDetails.getLastName());
        updatePersons.setAddress(personsDetails.getAddress());
        updatePersons.setCity(personsDetails.getCity());
        updatePersons.setZip(personsDetails.getZip());
        updatePersons.setPhone(personsDetails.getPhone());
        updatePersons.setEmail(personsDetails.getEmail());

        personsDao.save(updatePersons);

        return ResponseEntity.ok(updatePersons);
    }

    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<String> deletePersons(@PathVariable String firstName, @PathVariable String lastName) {

        boolean isDeleted = personsDao.delete(firstName, lastName);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(firstName);

    }

    @GetMapping(value = "/firestation")
    public ResponseEntity<String> getStationNumber(@RequestParam String stationNumber){
        //TODO firestation?stationNumber=<station_number>
/*
* Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers correspondante.
Donc, si le numéro de station = 1, elle doit renvoyer les habitants couverts par la station numéro 1. La liste
doit inclure les informations spécifiques suivantes : prénom, nom, adresse, numéro de téléphone. De plus,
elle doit fournir un décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
moins) dans la zone desservie
* */
        return null;
    }
}