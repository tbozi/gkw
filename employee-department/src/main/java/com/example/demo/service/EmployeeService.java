package com.example.demo.service;

import com.example.demo.models.DepartmentAvgSalaryDTO;
import com.example.demo.models.Employee;
import com.example.demo.repository.EmployeeRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class EmployeeService {
    @Inject
    private EmployeeRepo employeeRepo;

    public EmployeeService() {}

    public List<Employee> getAllEmployees() {
        return employeeRepo.getAllEmployees();
    }

    public Employee getEmployeeById(int id) {
        return employeeRepo.getEmployeeById(id);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepo.addEmployee(employee);
    }

    public Employee updateEmployee(int id, Employee employee) {
        return employeeRepo.updateEmployee(id, employee);
    }

    public boolean deleteEmployee(int id) {
        return employeeRepo.deleteEmployee(id);
    }

    public List<Employee> getEmployeesByDepartmentId(int departmentId) {
        return employeeRepo.getEmployeesByDepartmentId(departmentId);
    }

    public List<DepartmentAvgSalaryDTO> getAvgSalaryPerDepartment() {
        return employeeRepo.getAvgSalaryPerDepartment();
    }

    public List<Employee> getHighestSalaryEmployeesPerDepartment() {
        return employeeRepo.getHighestSalaryEmployeesPerDepartment();
    }
}
