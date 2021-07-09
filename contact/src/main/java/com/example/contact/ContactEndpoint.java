package com.example.contact;

import com.example.contact.model.Contact;
import com.example.contact.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactEndpoint {

    private ContactService contactService;

    public ContactEndpoint(final ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<Contact> findAll() {
        return this.contactService.findAll();
    }

    @PostMapping
    public Contact saveAndFlush(@RequestBody final Contact entity) {
        return this.contactService.saveAndFlush(entity);
    }

    @GetMapping("/{id}")
    public Contact findById(@RequestParam("id") final Long aLong) {
        return this.contactService.findById(aLong);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@RequestParam("id") final Long aLong) {
        this.contactService.deleteById(aLong);
    }
}
