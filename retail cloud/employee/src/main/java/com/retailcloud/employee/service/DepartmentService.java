package com.retailcloud.employee.service;

import com.retailcloud.employee.dto.DepartmentRequest;
import com.retailcloud.employee.dto.DepartmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DepartmentService {
    DepartmentResponse createDepartment(DepartmentRequest request);
    Optional<DepartmentResponse> updateDepartment(Long id, DepartmentRequest request);
    void deleteDepartment(Long id);
    Optional<DepartmentResponse> getDepartmentById(Long id, boolean expandEmployees);
    Page<DepartmentResponse> getAllDepartments(Pageable pageable, boolean expandEmployees);
}
