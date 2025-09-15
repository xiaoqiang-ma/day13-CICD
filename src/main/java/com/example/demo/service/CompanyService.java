package com.example.demo.service;

import com.example.demo.entity.Company;
import com.example.demo.repository.ICompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyService {
    private final ICompanyRepository companyRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CompanyService(ICompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    @Transactional
    public void empty() {
        jdbcTemplate.execute("delete from t_company;");
        jdbcTemplate.execute("alter table t_company AUTO_INCREMENT = 1;");
    }

    public List<Company> getCompanies(Integer page, Integer size) {
        if(page == null || size == null){
            return companyRepository.findAll();
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        return companyRepository.findAll(pageable).stream().toList();
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(int id, Company updatedCompany) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        updatedCompany.setId(id);
        return companyRepository.save(updatedCompany);
    }

    public Company getCompanyById(int id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        return company.orElse(null);
    }

    public void deleteCompany(int id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        companyRepository.delete(company.get());
    }
}
