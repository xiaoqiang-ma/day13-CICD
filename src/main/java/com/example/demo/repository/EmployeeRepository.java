package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        initData();
    }

    public List<Employee> getEmployeesByGender(String gender, Integer page, Integer size) {
        Stream<Employee> stream = employees.stream();
        if (gender != null) {
            stream = stream.filter(employee -> employee.getGender().compareToIgnoreCase(gender) == 0);
        }
        if (page != null && size != null) {
            stream = stream.skip((long) (page - 1) * size).limit(size);
        }
        return stream.toList();
    }

    public Employee getEmployeeById(int id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(employees.size() + 1);
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        for (Employee e : employees) {
            if (Objects.equals(e.getId(), id)) {
                e.setName(updatedEmployee.getName());
                e.setAge(updatedEmployee.getAge());
                e.setGender(updatedEmployee.getGender());
                e.setSalary(updatedEmployee.getSalary());
                return e;
            }
        }
        return null;
    }

    public void deleteEmployee(int id) {
        Employee employee = getEmployeeById(id);
        employees.remove(employee);
    }

    public void deleteAllEmployee(){
        employees.clear();
    }

    public void initData(){
        employees.add(new Employee(1, "Tom", 20, "MALE", 20000.0));
        employees.add(new Employee(2, "Micheal", 21, "MALE", 20001.0));
        employees.add(new Employee(3, "John", 22, "FEMALE", 20020.0));
        employees.add(new Employee(4, "David", 24, "MALE", 20003.0));
    }
}
