package com.example.contact.service;

import com.example.contact.model.Contact;
import com.example.contact.repo.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private ContactRepository contactRepository;

    public ContactService(final ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> findAll() {
        return this.contactRepository.findAll();
    }

    public Contact saveAndFlush(final Contact entity) {
        return this.contactRepository.saveAndFlush(entity);
    }

    public Contact findById(final Long aLong) {
        return this.contactRepository.findById(aLong).orElse(null);
    }

    public void deleteById(final Long aLong) {
        this.contactRepository.deleteById(aLong);
    }
}
