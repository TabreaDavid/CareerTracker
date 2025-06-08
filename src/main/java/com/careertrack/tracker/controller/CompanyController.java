package com.careertrack.tracker.controller;

import com.careertrack.tracker.model.Company;
import com.careertrack.tracker.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Company createCompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }
    
    @GetMapping("/{id}")
    public Company getCompany(@PathVariable Long id) {
        return companyRepository.findById(id).orElse(null);
    }
}
