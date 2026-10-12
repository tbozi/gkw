package com.example.demo.models;

import java.io.Serializable;

public class DepartmentAvgSalaryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int departmentId;
    private String departmentName;
    private double avgSalary;

    public DepartmentAvgSalaryDTO() {
    }

    public DepartmentAvgSalaryDTO(int departmentId, String departmentName, double avgSalary) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.avgSalary = avgSalary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public double getAvgSalary() {
        return avgSalary;
    }

    public void setAvgSalary(double avgSalary) {
        this.avgSalary = avgSalary;
    }
}
