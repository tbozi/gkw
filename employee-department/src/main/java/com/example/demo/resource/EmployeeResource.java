package com.example.demo.resource;

import com.example.demo.models.DepartmentAvgSalaryDTO;
import com.example.demo.models.Employee;
import com.example.demo.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    @Inject
    private EmployeeService employeeService;

    @GET
    public Response getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return Response.ok(employees).build();
    }

    // 2. GET: Lấy nhân viên theo ID
    @GET
    @Path("/{id}")
    public Response getEmployeeById(@PathParam("id") int id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Không tìm thấy nhân viên với ID = " + id + "\"}")
                    .build();
        }
        return Response.ok(employee).build();
    }

    // 3. POST: Thêm mới nhân viên
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEmployee(Employee employee) {
        Employee newEmployee = employeeService.addEmployee(employee);
        return Response.status(Response.Status.CREATED).entity(newEmployee).build();
    }

    // 4. PUT: Cập nhật nhân viên theo ID
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("id") int id, Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        if (updatedEmployee == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Không tìm thấy nhân viên để cập nhật\"}")
                    .build();
        }
        return Response.ok(updatedEmployee).build();
    }

    // 5. DELETE: Xóa nhân viên theo ID
    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@PathParam("id") int id) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Nhân viên không tồn tại hoặc đã bị xóa\"}")
                    .build();
        }
        return Response.ok("{\"message\": \"Xóa thành công nhân viên ID " + id + "\"}").build();
    }

    // 6. GET: Tính lương trung bình theo từng phòng ban
    @GET
    @Path("/avg-salary")
    public Response getAvgSalaryPerDepartment() {
        List<DepartmentAvgSalaryDTO> list = employeeService.getAvgSalaryPerDepartment();
        return Response.ok(list).build();
    }

    // 7. GET: Nhân viên có lương cao nhất ở mỗi phòng ban
    @GET
    @Path("/highest-salary")
    public Response getHighestSalaryEmployeesPerDepartment() {
        List<Employee> list = employeeService.getHighestSalaryEmployeesPerDepartment();
        return Response.ok(list).build();
    }
}
