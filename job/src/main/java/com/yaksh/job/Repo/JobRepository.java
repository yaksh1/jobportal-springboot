package com.yaksh.job.Repo;

import com.yaksh.job.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    Job getById(Long id);
}
