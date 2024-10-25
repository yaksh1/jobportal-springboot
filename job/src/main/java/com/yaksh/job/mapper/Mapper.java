package com.yaksh.job.mapper;

import com.yaksh.job.DTO.JobDTO;
import com.yaksh.job.external.Company;
import com.yaksh.job.external.Review;
import com.yaksh.job.model.Job;

import java.util.List;

public class Mapper {

    public static JobDTO mapToJobDTO(Job job, Company company, List<Review> review){
        return JobDTO.builder()
                .jobId(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .minSalary(job.getMinSalary())
                .maxSalary(job.getMaxSalary())
                .location(job.getLocation())
                .company(company)
                .review(review)
                .build();
    }
}
