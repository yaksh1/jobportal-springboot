package com.yaksh.job.service;


import com.yaksh.job.DTO.JobDTO;
import com.yaksh.job.Repo.JobRepository;
import com.yaksh.job.Util.ResponseDataDTO;
import com.yaksh.job.external.Company;
import com.yaksh.job.external.Review;
import com.yaksh.job.mapper.Mapper;
import com.yaksh.job.model.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobService {
    @Autowired
    RestTemplate restTemplate;

    @Value("${company.service.url}")
    String company_url;
    @Value("${review.service.url}")
    String review_url;

    private final JobRepository jobRepository;
    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(job -> convertToResponse(job)).collect(Collectors.toList());
    }

    private JobDTO convertToResponse(Job job) {
        Company company = restTemplate.getForObject(company_url+"/companies/"+job.getCompanyId(), Company.class);
        ResponseEntity<ResponseDataDTO> dto = restTemplate.exchange(review_url+"/reviews?companyId=" + company.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDataDTO>() {
                }
        );
        List<Review> review = (List<Review>) dto.getBody().getData();
        System.out.println(review);
        return Mapper.mapToJobDTO(job,company,review);
    }

    public ResponseDataDTO saveJob(Job job) {
        try{
            jobRepository.save(job);
            return new ResponseDataDTO("job saved",true);
        }catch (Exception e){
            return new ResponseDataDTO(e.getMessage(),false);
        }
    }

    public ResponseDataDTO deleteJobById(Long id) {
        try{
            jobRepository.deleteById(id);
            return new ResponseDataDTO("job deleted",true);
        }catch (Exception e){
            return new ResponseDataDTO(e.getMessage(),false);
        }
    }

    public ResponseDataDTO saveAll(List<Job> jobs) {
        try {
            jobRepository.saveAll(jobs);
            return new ResponseDataDTO("job saved",true,jobs);
        }catch (Exception e){
            return new ResponseDataDTO(e.getMessage(),false);
        }

    }

    public ResponseDataDTO deleteAll() {
        try {
            jobRepository.deleteAll();
            return new ResponseDataDTO("All jobs deleted",true);
        }catch (Exception e){
            return new ResponseDataDTO(e.getMessage(),false);
        }
    }

    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if(job==null){
            return null;
        }
        return convertToResponse(job);
    }

    public ResponseDataDTO updateById(Long id, Job updatedJob) {
        if(jobRepository.existsById(id)){
            Job job = jobRepository.getById(id);
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setLocation(updatedJob.getLocation());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setMinSalary(updatedJob.getMinSalary());
            try{
                jobRepository.save(job);
                return new ResponseDataDTO("job updated",true);
            }catch (Exception e){
                return new ResponseDataDTO("job update failed",false,"error: "+e.getMessage());
            }
        }else{
            return new ResponseDataDTO("job update failed",false,"error: Job not found");
        }
    }
}
