package com.example.demo.Impl;

import com.example.demo.models.DepartmentAvgSalaryDTO;
import com.example.demo.models.Employee;
import com.example.demo.repository.EmployeeRepo;
import jakarta.enterprise.context.ApplicationScoped;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EmployeeRepoImpl implements EmployeeRepo {

    private volatile DataSource dataSource;

    private DataSource getDataSource() {
        if (dataSource == null) {
            try {
                Context env = (Context) new InitialContext().lookup("java:comp/env");
                dataSource = (DataSource) env.lookup("jdbc/employee_db");
            } catch (NamingException e) {
                throw new RuntimeException("Không tìm thấy DataSource jdbc/hrdb", e);
            }
        }
        return dataSource;
    }
    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT id, name, role, salary, department_id FROM employees";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setRole(rs.getString("role"));
                emp.setSalary(rs.getDouble("salary"));
                emp.setDepartmentId(rs.getInt("department_id"));
                list.add(emp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Employee getEmployeeById(int id) {
        String sql = "SELECT id, name, role, salary, department_id FROM employees WHERE id = ?";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("id"));
                    emp.setName(rs.getString("name"));
                    emp.setRole(rs.getString("role"));
                    emp.setSalary(rs.getDouble("salary"));
                    emp.setDepartmentId(rs.getInt("department_id"));
                    return emp;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (name, role, salary, department_id) VALUES (?, ?, ?, ?)";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getRole());
            ps.setDouble(3, employee.getSalary());
            ps.setInt(4, employee.getDepartmentId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    employee.setId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employee;
    }

    @Override
    public Employee updateEmployee(int id, Employee employee) {
        String sql = "UPDATE employees SET name = ?, role = ?, salary = ?, department_id = ? WHERE id = ?";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getRole());
            ps.setDouble(3, employee.getSalary());
            ps.setInt(4, employee.getDepartmentId());
            ps.setInt(5, id);
            int rows = ps.executeUpdate();
            if (rows == 0) return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        employee.setId(id);
        return employee;
    }

    @Override
    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getEmployeesByDepartmentId(int departmentId) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT id, name, role, salary, department_id FROM employees WHERE department_id = ?";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("id"));
                    emp.setName(rs.getString("name"));
                    emp.setRole(rs.getString("role"));
                    emp.setSalary(rs.getDouble("salary"));
                    emp.setDepartmentId(rs.getInt("department_id"));
                    list.add(emp);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<DepartmentAvgSalaryDTO> getAvgSalaryPerDepartment() {
        List<DepartmentAvgSalaryDTO> list = new ArrayList<>();
        String sql = "SELECT d.id AS dept_id, d.name AS dept_name, COALESCE(AVG(e.salary), 0) AS avg_sal " +
                     "FROM departments d " +
                     "LEFT JOIN employees e ON d.id = e.department_id " +
                     "GROUP BY d.id, d.name " +
                     "ORDER BY d.id";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DepartmentAvgSalaryDTO dto = new DepartmentAvgSalaryDTO();
                dto.setDepartmentId(rs.getInt("dept_id"));
                dto.setDepartmentName(rs.getString("dept_name"));
                dto.setAvgSalary(rs.getDouble("avg_sal"));
                list.add(dto);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Employee> getHighestSalaryEmployeesPerDepartment() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT e.id, e.name, e.role, e.salary, e.department_id " +
                     "FROM employees e " +
                     "WHERE e.salary = (" +
                     "    SELECT MAX(e2.salary) " +
                     "    FROM employees e2 " +
                     "    WHERE e2.department_id = e.department_id" +
                     ") " +
                     "ORDER BY e.department_id";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setRole(rs.getString("role"));
                emp.setSalary(rs.getDouble("salary"));
                emp.setDepartmentId(rs.getInt("department_id"));
                list.add(emp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
