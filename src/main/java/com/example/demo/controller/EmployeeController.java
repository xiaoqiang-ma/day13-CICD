package com.example.demo.controller;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.dto.mapper.EmployeeMapper;
import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<EmployeeResponse> getEmployees(@RequestParam(required = false) String gender, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return employeeMapper.toResponse(employeeService.getEmployeesByGender(gender, page, size));
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable int id) {
        return employeeMapper.toResponse(employeeService.getEmployeeById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createEmployee(@RequestBody @Validated EmployeeRequest employee) {
        return employeeMapper.toResponse(employeeService.createEmployee(employeeMapper.toEntity(employee)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponse updateEmployee(@PathVariable int id, @RequestBody @Validated EmployeeRequest updatedEmployee) {
        return employeeMapper.toResponse(employeeService.updateEmployee(id, employeeMapper.toEntity(updatedEmployee)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
    }

    @DeleteMapping("/all")
    public void empty() {
        employeeService.deleteAllEmployee();
    }

}
