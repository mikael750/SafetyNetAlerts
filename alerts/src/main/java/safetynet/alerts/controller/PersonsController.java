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

    @GetMapping(value = "/person")
    public List<Persons> listePersons() {
        return personsDao.findAll();
    }

    @GetMapping(value = "/person/{id}")
    public Persons afficherUnePersonne(@PathVariable int id) {
        return personsDao.findById(id);
    }

    @PostMapping(value = "/person")
    public ResponseEntity<Persons> ajouterPersons(@RequestBody Persons persons) {
        Persons personsAdded = personsDao.save(persons);
        if (Objects.isNull(personsAdded)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personsAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/person/{id}")
    public ResponseEntity<Persons> updatePersons(@PathVariable int id,@RequestBody Persons personsDetails) throws Exception {
        Persons updatePersons = personsDao.findById(id);
        if (updatePersons == null){
            throw new Exception("Cette personne n'existe pas avec l'id: " + id);
        }

        updatePersons.setFirstName(personsDetails.getFirstName());
        updatePersons.setLastName(personsDetails.getLastName());
        updatePersons.setAddress(personsDetails.getAddress());
        updatePersons.setCity(personsDetails.getCity());
        updatePersons.setZip(personsDetails.getZip());
        updatePersons.setPhone(personsDetails.getPhone());
        updatePersons.setEmail(personsDetails.getEmail());

        personsDao.save(updatePersons);

        return ResponseEntity.ok(updatePersons);
    }

    @DeleteMapping(value = "/person/{id}")
    public ResponseEntity<String> deletePersons(@PathVariable String id) {

        boolean isDeleted = personsDao.delete(Integer.valueOf(id));

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(id);

    }

/*
    @GetMapping("/persons")
    public String listePersons() {
		return "Une personne est appeller en exemple";
    }

	@GetMapping("/persons/{id}")
	public String afficherPersonne(@PathVariable int id) {
		return "Vous avez demandé une personne avec l'id  " + id;
	}

	//Récupérer un produit par son Id
	@GetMapping(value = "/persons/{id}")
	public Persons afficherUnePersonne(@PathVariable int id) {
		Persons persons = new Persons(id, new String("John"), new String("Boyd"), new String("1509 Culver St"), new String("Culver")
		new String("97451"),new String("841-874-6512"), new String("jaboyd@email.com"));
		return persons;
	}
*/
}