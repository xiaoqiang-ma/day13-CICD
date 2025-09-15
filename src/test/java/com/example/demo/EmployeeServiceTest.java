package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.exception.InactiveEmployeeException;
import com.example.demo.exception.InvalidAgeAndSalaryEmployeeException;
import com.example.demo.exception.InvalidEmployeeAgeException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.IEmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private IEmployeeRepository employeeRepository;

    // given a middle age when create employee then create success
    @Test
    void should_create_success_when_create_a_middle_age_employee() {
        Employee employee = new Employee(1, "Tom", 20, "Male", 20000.1);
        when(employeeRepository.save(any())).thenReturn(employee);

        Employee expectEmployee = employeeService.createEmployee(employee);

        assertEquals(expectEmployee.getAge(), employee.getAge());
    }

    // given a less than 18 or greater than 65 age when create employee then create failed
    @Test
    void should_create_failed_when_create_a_less_than_18_or_greater_than_65_employee() {
        Employee employee = new Employee(1, "Tom", 80, "Male", 20000.1);
//        when(employeeRepository.createEmployee(any())).thenReturn(employee);

        InvalidEmployeeAgeException e = assertThrows(InvalidEmployeeAgeException.class, () -> employeeService.createEmployee(employee));
        assertEquals("employee age greater than 65 or less than 18!", e.getMessage());
    }

    // given a greater than 30 age and salary below 20000 when create employee then create failed
    @Test
    void should_create_failed_when_create_employee_of_greater_than_30_and_salary_below_20000() {
        Employee employee = new Employee(1, "Tom", 35, "Male", 100.0);
        assertThrows(InvalidAgeAndSalaryEmployeeException.class, ()-> employeeService.createEmployee(employee));
    }


    @Test
    void should_is_active_when_create_employee(){
        Employee employee = new Employee(1, "Tom", 25, "Male", 100.0);
        employee.setActive(true);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        Employee employeeResult = employeeService.createEmployee(employee);
        assertEquals(true, employeeResult.getActive());
    }

    @Test
    void should_throw_exception_when_update_inactive_employee(){
        Employee employee = new Employee(1, "Tom", 25, "Male", 100.0);
        employee.setActive(false);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        assertThrows(InactiveEmployeeException.class, ()-> employeeService.updateEmployee(1, employee));
    }

}
