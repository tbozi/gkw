package com.example.demo.repository;

import com.example.demo.models.Department;

import java.util.List;

public interface DepartmentRepo {
    List<Department> getAllDepartments();
    Department getDepartmentById(int id);
    Department addDepartment(Department department);
    Department updateDepartment(int id, Department department);
    boolean deleteDepartment(int id);
}
