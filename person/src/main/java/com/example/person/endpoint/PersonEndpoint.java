package com.example.person.endpoint;

import com.example.person.model.Person;
import com.example.person.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonEndpoint {

    private PersonService personService;

    public PersonEndpoint(final PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> findAll() {
        return this.personService.findAll();
    }

    @PostMapping
    public Person saveAndFlush(@RequestBody final Person entity) {
        return this.personService.saveAndFlush(entity);
    }

    @PutMapping
    public Person update(@RequestBody final Person entity) {
        return this.personService.saveAndFlush(entity);
    }

    @GetMapping("/{id}")
    public Person findById(@RequestParam("id") final Long id) {
        return this.personService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@RequestParam("id") final Long id) {
        this.personService.deleteById(id);
    }
}
