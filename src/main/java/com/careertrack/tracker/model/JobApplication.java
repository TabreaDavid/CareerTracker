package com.careertrack.tracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class JobApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    
    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    private LocalDateTime appliedAt;
    private LocalDateTime lastUpdatedAt;
    
    private String notes;
    
    public JobApplication() {}
    
    public JobApplication(String title, Company company, Status status) {
        this.title = title;
        this.company = company;
        this.status = status;
        this.appliedAt = LocalDateTime.now();
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
