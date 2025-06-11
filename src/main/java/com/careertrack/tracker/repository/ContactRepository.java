package com.careertrack.tracker.repository;

import com.careertrack.tracker.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
