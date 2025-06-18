package com.careertrack.tracker.controller;

import com.careertrack.tracker.model.JobApplication;
import com.careertrack.tracker.model.Status;
import com.careertrack.tracker.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public JobApplication createApplication(@RequestBody JobApplication application) {
        return jobApplicationRepository.save(application);
    }
    
    @GetMapping("/{id}")
    public JobApplication getApplication(@PathVariable Long id) {
        return jobApplicationRepository.findById(id).orElse(null);
    }
    
    @PatchMapping("/{id}/status")
    public JobApplication updateStatus(@PathVariable Long id, @RequestParam Status status) {
        JobApplication app = jobApplicationRepository.findById(id).orElse(null);
        if (app != null) {
            app.setStatus(status);
            return jobApplicationRepository.save(app);
        }
        return null;
    }
    
    // TODO: add pagination later
    // TODO: add proper error handling
}
