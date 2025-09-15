package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.exception.InactiveEmployeeException;
import com.example.demo.exception.InvalidAgeAndSalaryEmployeeException;
import com.example.demo.exception.InvalidEmployeeAgeException;
import com.example.demo.repository.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Employee> getEmployeesByGender(String gender, Integer page, Integer size) {
        if (gender == null) {
            if (page == null || size == null) {
                return employeeRepository.findAll();
            } else {
                Pageable pageable = PageRequest.of(page - 1, size);
                return employeeRepository.findAll(pageable).stream().toList();
            }
        } else {
            if (page == null || size == null) {
                return employeeRepository.findEmployeeByGender(gender);
            } else {
                Pageable pageable = PageRequest.of(page - 1, size);
                return employeeRepository.findEmployeeByGender(gender, pageable);
            }
        }
    }

    public Employee getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        return employee.get();
    }

    public Employee createEmployee(Employee employee) {
        Integer age = employee.getAge();
        if (age == null) {
            throw new InvalidEmployeeAgeException("employee age is empty!");
        }
        if (age > 65 || age < 18) {
            throw new InvalidEmployeeAgeException("employee age greater than 65 or less than 18!");
        }
        Double salary = employee.getSalary();
        if (salary == null) {
            throw new InvalidAgeAndSalaryEmployeeException("employee salary is empty!");
        }
        if (age >= 30 && salary < 20000.0) {
            throw new InvalidAgeAndSalaryEmployeeException("employee salary below 20000.0 and age over 30!");
        }
        employee.setActive(true);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        if (!employee.get().getActive()) {
            throw new InactiveEmployeeException("This employee is inactive!");
        }
        updatedEmployee.setId(id);
        return employeeRepository.save(updatedEmployee);
    }

    public void deleteEmployee(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        employee.get().setActive(false);
        employeeRepository.save(employee.get());
    }

    public void deleteAllEmployee() {
        jdbcTemplate.execute("truncate table t_employee;");
    }

}
