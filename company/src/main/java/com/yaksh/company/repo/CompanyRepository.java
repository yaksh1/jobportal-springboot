package com.yaksh.company.repo;

import com.yaksh.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    Company getById(Long id);
}
