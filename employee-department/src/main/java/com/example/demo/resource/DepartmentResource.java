package com.example.demo.resource;

import com.example.demo.models.Department;
import com.example.demo.models.Employee;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {
    @Inject
    private DepartmentService departmentService;

    @Inject
    private EmployeeService employeeService;

    // 1. GET: Danh sách phòng ban
    @GET
    public Response getAllDepartments() {
        List<Department> list = departmentService.getAllDepartments();
        return Response.ok(list).build();
    }

    // 2. GET: Chi tiết 1 phòng ban
    @GET
    @Path("/{id}")
    public Response getDepartmentById(@PathParam("id") int id) {
        Department dept = departmentService.getDepartmentById(id);
        if (dept == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Không tìm thấy phòng ban\"}")
                    .build();
        }
        return Response.ok(dept).build();
    }

    // 3. GET: Danh sách nhân viên của một phòng ban cụ thể (Yêu cầu Bài 6)
    @GET
    @Path("/{id}/employees")
    public Response getEmployeesByDepartment(@PathParam("id") int id) {
        Department dept = departmentService.getDepartmentById(id);
        if (dept == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Phòng ban không tồn tại\"}")
                    .build();
        }
        List<Employee> employees = employeeService.getEmployeesByDepartmentId(id);
        return Response.ok(employees).build();
    }

    // 4. POST: Thêm mới phòng ban
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDepartment(Department department) {
        Department newDept = departmentService.addDepartment(department);
        return Response.status(Response.Status.CREATED).entity(newDept).build();
    }

    // 5. PUT: Cập nhật thông tin phòng ban
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDepartment(@PathParam("id") int id, Department department) {
        Department updatedDept = departmentService.updateDepartment(id, department);
        if (updatedDept == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Không tìm thấy phòng ban để cập nhật\"}")
                    .build();
        }
        return Response.ok(updatedDept).build();
    }

    // 6. DELETE: Xóa phòng ban
    @DELETE
    @Path("/{id}")
    public Response deleteDepartment(@PathParam("id") int id) {
        boolean deleted = departmentService.deleteDepartment(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Phòng ban không tồn tại\"}")
                    .build();
        }
        return Response.ok("{\"message\": \"Xóa phòng ban thành công\"}").build();
    }
}
