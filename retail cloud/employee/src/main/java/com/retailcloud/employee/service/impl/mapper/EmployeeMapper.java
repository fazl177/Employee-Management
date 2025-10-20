package com.retailcloud.employee.service.impl.mapper;

import com.retailcloud.employee.dto.EmployeeRequest;
import com.retailcloud.employee.dto.EmployeeResponse;
import com.retailcloud.employee.entity.Department;
import com.retailcloud.employee.entity.Employee;

public class EmployeeMapper {
    public static Employee toEntity(EmployeeRequest dto, Employee manager, Department department) {
        Employee e = new Employee();
        e.setName(dto.getName());
        e.setDob(dto.getDob());
        e.setSalary(dto.getSalary());
        e.setAddress(dto.getAddress());
        e.setRoleTitle(dto.getRoleTitle());
        e.setJoiningDate(dto.getJoiningDate());
        e.setYearlyBonusPercentage(dto.getYearlyBonusPercentage());
        e.setManager(manager);
        e.setDepartment(department);
        return e;
    }

    public static EmployeeResponse toResponse(Employee emp) {
        if (emp == null) return null;
        EmployeeResponse resp = new EmployeeResponse();
        resp.setId(emp.getId());
        resp.setName(emp.getName());
        resp.setDob(emp.getDob());
        resp.setSalary(emp.getSalary());
        resp.setAddress(emp.getAddress());
        resp.setRoleTitle(emp.getRoleTitle());
        resp.setJoiningDate(emp.getJoiningDate());
        resp.setYearlyBonusPercentage(emp.getYearlyBonusPercentage());
        resp.setManagerId(emp.getManager() != null ? emp.getManager().getId() : null);
        resp.setDepartmentId(emp.getDepartment() != null ? emp.getDepartment().getId() : null);
        // friendly names (requires being called inside a transactional context to avoid LAZY issues)
        resp.setManagerName(emp.getManager() != null ? emp.getManager().getName() : null);
        resp.setDepartmentName(emp.getDepartment() != null ? emp.getDepartment().getName() : null);
        return resp;
    }
}