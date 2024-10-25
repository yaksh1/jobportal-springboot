package com.yaksh.company.controller;

import com.yaksh.company.Util.ResponseDataDTO;
import com.yaksh.company.dto.CompanyResponseDTO;
import com.yaksh.company.model.Company;
import com.yaksh.company.repo.CompanyRepository;
import com.yaksh.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public List<CompanyResponseDTO> getAllCompanies(){
        return companyService.getAllCompanies();
    }

    @PostMapping
    public ResponseDataDTO saveCompany(@RequestBody Company company){
        return companyService.saveCompany(company);
    }

    @PostMapping("/saveAllCompanies")
    public ResponseEntity<ResponseDataDTO> saveAllCompanies(@RequestBody List<Company> companies){
        ResponseDataDTO companyResponseDTO = companyService.saveAllCompanies(companies);
        if(companyResponseDTO.isStatus()){
            return ResponseEntity.ok(companyResponseDTO);
        }
        return new ResponseEntity(companyResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseDataDTO updateCompany(@PathVariable Long id,@RequestBody Company updatedCompany){
        return companyService.updateCompany(id,updatedCompany);
    }

    @DeleteMapping("/{id}")
    public ResponseDataDTO deleteCompanyById(@PathVariable Long id){
        return companyService.deleteCompanyById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        Company company = companyService.getCompanyById(id);
        if(company==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }
}
