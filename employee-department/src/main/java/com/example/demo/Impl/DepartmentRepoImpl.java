package com.example.demo.Impl;

import com.example.demo.models.Department;
import com.example.demo.repository.DepartmentRepo;
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
public class DepartmentRepoImpl implements DepartmentRepo {

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
    public List<Department> getAllDepartments() {

        List<Department> list = new ArrayList<>();
        String sql = "SELECT id, name FROM departments";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Department(rs.getInt("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Department getDepartmentById(int id) {
        String sql = "SELECT id, name FROM departments WHERE id = ?";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Department(rs.getInt("id"), rs.getString("name"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Department addDepartment(Department department) {
        String sql = "INSERT INTO departments (name) VALUES (?)";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, department.getName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    department.setId(rs.getInt(1));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return department;
    }

    @Override
    public Department updateDepartment(int id, Department department) {
        String sql = "UPDATE departments SET name = ? WHERE id = ?";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, department.getName());
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            if (rows == 0) return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        department.setId(id);
        return department;
    }

    @Override
    public boolean deleteDepartment(int id) {
        String sql = "DELETE FROM departments WHERE id = ?";
        try (Connection con = getDataSource().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
