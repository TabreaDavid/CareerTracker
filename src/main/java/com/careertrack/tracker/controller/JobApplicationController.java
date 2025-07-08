package com.careertrack.tracker.controller;

import com.careertrack.tracker.exception.ResourceNotFoundException;
import com.careertrack.tracker.model.JobApplication;
import com.careertrack.tracker.model.Status;
import com.careertrack.tracker.repository.JobApplicationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
    
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    
    @GetMapping
    public Page<JobApplication> getAllApplications(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastUpdatedAt") String sortBy) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        
        if (status != null) {
            return jobApplicationRepository.findByStatus(status, pageable);
        }
        if (companyId != null) {
            return jobApplicationRepository.findByCompanyId(companyId, pageable);
        }
        return jobApplicationRepository.findAll(pageable);
    }
    
    @PostMapping
    public JobApplication createApplication(@Valid @RequestBody JobApplication application) {
        return jobApplicationRepository.save(application);
    }
    
    @GetMapping("/{id}")
    public JobApplication getApplication(@PathVariable Long id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
    }
    
    @PutMapping("/{id}")
    public JobApplication updateApplication(@PathVariable Long id, @Valid @RequestBody JobApplication updated) {
        return jobApplicationRepository.findById(id)
                .map(app -> {
                    app.setTitle(updated.getTitle());
                    app.setCompany(updated.getCompany());
                    app.setContact(updated.getContact());
                    app.setStatus(updated.getStatus());
                    // Fix: handle null notes gracefully
                    if (updated.getNotes() != null) {
                        app.setNotes(updated.getNotes());
                    }
                    return jobApplicationRepository.save(app);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
    }
    
    @PatchMapping("/{id}/status")
    public JobApplication updateStatus(@PathVariable Long id, @RequestParam Status status) {
        JobApplication app = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        app.setStatus(status);
        return jobApplicationRepository.save(app);
    }
    
    @GetMapping("/stats/by-status")
    public Map<String, Long> getStatsByStatus() {
        Map<String, Long> stats = new HashMap<>();
        for (Status status : Status.values()) {
            stats.put(status.name(), jobApplicationRepository.countByStatus(status));
        }
        return stats;
    }
    
    @GetMapping("/company/{companyId}")
    public List<JobApplication> getApplicationsByCompany(@PathVariable Long companyId) {
        return jobApplicationRepository.findByCompanyId(companyId);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        if (!jobApplicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Application not found with id: " + id);
        }
        jobApplicationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
