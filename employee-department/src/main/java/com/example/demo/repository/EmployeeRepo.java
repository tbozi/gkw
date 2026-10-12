package com.example.demo.repository;

import com.example.demo.models.DepartmentAvgSalaryDTO;
import com.example.demo.models.Employee;

import java.util.List;

public interface EmployeeRepo {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(int id);
    Employee addEmployee(Employee employee);
    Employee updateEmployee(int id, Employee employee);
    boolean deleteEmployee(int id);
    List<Employee> getEmployeesByDepartmentId(int departmentId);

    // Nghiệp vụ logic 1-N cho thi giữa kỳ
    List<DepartmentAvgSalaryDTO> getAvgSalaryPerDepartment();
    List<Employee> getHighestSalaryEmployeesPerDepartment();
}
