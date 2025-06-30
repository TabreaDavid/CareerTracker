package com.careertrack.tracker.repository;

import com.careertrack.tracker.model.JobApplication;
import com.careertrack.tracker.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByStatus(Status status);
    List<JobApplication> findByCompanyId(Long companyId);
    long countByStatus(Status status);
    
    Page<JobApplication> findByStatus(Status status, Pageable pageable);
    Page<JobApplication> findByCompanyId(Long companyId, Pageable pageable);
}
