package com.yaksh.job.controller;


import com.yaksh.job.DTO.JobDTO;
import com.yaksh.job.Util.ResponseDataDTO;
import com.yaksh.job.model.Job;
import com.yaksh.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    @GetMapping
    public List<JobDTO> getAllJobs(){
        return jobService.getAllJobs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        JobDTO jobDTO = jobService.getJobById(id);
        if(jobDTO ==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jobDTO);
    }
    @PostMapping
    public ResponseDataDTO saveJob(@RequestBody Job job){
        return jobService.saveJob(job);
    }

    @DeleteMapping("/deleteJob/{id}")
    public ResponseDataDTO deleteJobById(@PathVariable("id") Long id){
        return jobService.deleteJobById(id);
    }

    @PostMapping("/saveAll")
    public ResponseDataDTO saveMultipleJobs(@RequestBody List<Job> jobs){
        return jobService.saveAll(jobs);
    }

    @DeleteMapping("/deleteAll")
    public ResponseDataDTO deleteAll(){
        return jobService.deleteAll();
    }

    @PutMapping("/update/{id}")
    public ResponseDataDTO updateById(@PathVariable Long id,@RequestBody Job updatedJob){
        return jobService.updateById(id,updatedJob);
    }
}
