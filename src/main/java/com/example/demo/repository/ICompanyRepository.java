package com.example.demo.repository;

import com.example.demo.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ICompanyRepository extends JpaRepository<Company, Integer> {
}
