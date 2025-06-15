package com.careertrack.tracker.controller;

import com.careertrack.tracker.model.Contact;
import com.careertrack.tracker.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    
    @Autowired
    private ContactRepository contactRepository;
    
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
    
    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }
    
    @GetMapping("/{id}")
    public Contact getContact(@PathVariable Long id) {
        return contactRepository.findById(id).orElse(null);
    }
}
