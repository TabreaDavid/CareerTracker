package com.careertrack.tracker.controller;

import com.careertrack.tracker.exception.ResourceNotFoundException;
import com.careertrack.tracker.model.JobApplication;
import com.careertrack.tracker.model.Status;
import com.careertrack.tracker.repository.JobApplicationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<JobApplication> getAllApplications(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Long companyId) {
        
        if (status != null) {
            return jobApplicationRepository.findByStatus(status);
        }
        if (companyId != null) {
            return jobApplicationRepository.findByCompanyId(companyId);
        }
        return jobApplicationRepository.findAll();
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
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        if (!jobApplicationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Application not found with id: " + id);
        }
        jobApplicationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    // TODO: add pagination later
}
