package com.retailcloud.employee.service;

import com.retailcloud.employee.dto.EmployeeRequest;
import com.retailcloud.employee.dto.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest request);
    Optional<EmployeeResponse> updateEmployee(Long id, EmployeeRequest request);
    Optional<EmployeeResponse> getEmployeeById(Long id);
    Page<EmployeeResponse> getAllEmployees(Pageable pageable, boolean lookup);
    void deleteEmployee(Long id);
    void moveEmployee(Long employeeId, Long newDepartmentId);
}
