package com.yaksh.company.service;

import com.yaksh.company.Util.ResponseDataDTO;
import com.yaksh.company.dto.CompanyResponseDTO;
import com.yaksh.company.model.Company;
import com.yaksh.company.repo.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public List<CompanyResponseDTO> getAllCompanies() {
        List<Company> companies= companyRepository.findAll();
        return companies.stream().map(company -> mapToResponse(company)).collect(Collectors.toList());
    }

    private CompanyResponseDTO mapToResponse(Company company) {
        return CompanyResponseDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .build();
    }

    public ResponseDataDTO saveCompany(Company company) {
        try{
            companyRepository.save(company);
            return new ResponseDataDTO("company added.",true,company);
        }catch (Exception e){
            return new ResponseDataDTO("company not added.",false,"error: "+e.getMessage());
        }
    }

    public ResponseDataDTO updateCompany(Long id, Company updatedCompany) {
        if(companyRepository.existsById(id)){
            Company company = companyRepository.getById(id);
            company.setName(updatedCompany.getName());
            company.setDescription(updatedCompany.getDescription());
//            company.setJobs(updatedCompany.getJobs());
//            company.setReviews(updatedCompany.getReviews());
            try{
                companyRepository.save(company);
                return new ResponseDataDTO("company updated",true);
            }catch (Exception e){
                return new ResponseDataDTO("company update failed",false,"error: "+e.getMessage());
            }
        }else{
            return new ResponseDataDTO("company update failed",false,"error: company not found");
        }
    }

    public ResponseDataDTO deleteCompanyById(Long id) {
        try{
            companyRepository.deleteById(id);
            return new ResponseDataDTO("company deleted.",true);
        }catch (Exception e){
            return new ResponseDataDTO("company deletion failed.",false,"error: "+e.getMessage());
        }
    }

    public Company getCompanyById(Long id) {
        try{
           Company company = companyRepository.findById(id).orElse(null);
            CompanyResponseDTO companyResponseDTO= CompanyResponseDTO.builder()
                    .id(company.getId())
                    .name(company.getName())
                    .description(company.getDescription())
                    .build();
            return company;
//            return new ResponseDataDTO("company found",true,companyResponseDTO);

        }catch (Exception e){
            return null;
//            return new ResponseDataDTO("company not found",false,"error: "+e.getMessage());
        }
    }

    public ResponseDataDTO saveAllCompanies(List<Company> companies) {
        try{
            companyRepository.saveAll(companies);
            return new ResponseDataDTO("companies saved successfully",true);
        }catch (Exception e){
            return new ResponseDataDTO("companies not added.",false,"error: "+e.getMessage());
        }
    }
}
