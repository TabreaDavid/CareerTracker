package com.careertrack.tracker.controller;

import com.careertrack.tracker.model.JobApplication;
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
    public List<JobApplication> getAllApplications() {
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
    
    // TODO: add filtering by status and company
    // TODO: add status update endpoint
}
