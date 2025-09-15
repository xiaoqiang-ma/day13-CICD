package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findEmployeeByGender(String gender, Pageable pageable);


    List<Employee> findEmployeeByGender(String gender);
}
