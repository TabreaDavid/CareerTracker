package com.careertrack.tracker.controller;

import com.careertrack.tracker.exception.ResourceNotFoundException;
import com.careertrack.tracker.model.Contact;
import com.careertrack.tracker.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Contact createContact(@Valid @RequestBody Contact contact) {
        return contactRepository.save(contact);
    }
    
    @GetMapping("/{id}")
    public Contact getContact(@PathVariable Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
    }
    
    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id, @Valid @RequestBody Contact updatedContact) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setName(updatedContact.getName());
                    contact.setEmail(updatedContact.getEmail());
                    contact.setPhone(updatedContact.getPhone());
                    contact.setCompany(updatedContact.getCompany());
                    return contactRepository.save(contact);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contact not found with id: " + id);
        }
        contactRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
