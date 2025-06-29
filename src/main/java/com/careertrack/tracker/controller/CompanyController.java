package com.careertrack.tracker.controller;

import com.careertrack.tracker.exception.ResourceNotFoundException;
import com.careertrack.tracker.model.Company;
import com.careertrack.tracker.repository.CompanyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @GetMapping
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
    
    @PostMapping
    public Company createCompany(@Valid @RequestBody Company company) {
        return companyRepository.save(company);
    }
    
    @GetMapping("/{id}")
    public Company getCompany(@PathVariable Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        if (!companyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Company not found with id: " + id);
        }
        companyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
