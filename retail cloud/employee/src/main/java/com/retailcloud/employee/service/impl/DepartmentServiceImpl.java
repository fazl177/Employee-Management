package com.retailcloud.employee.service.impl;

import com.retailcloud.employee.dto.DepartmentRequest;
import com.retailcloud.employee.dto.DepartmentResponse;
import com.retailcloud.employee.dto.EmployeeResponse;
import com.retailcloud.employee.entity.Department;
import com.retailcloud.employee.entity.Employee;
import com.retailcloud.employee.exception.ResourceNotFoundException;
import com.retailcloud.employee.repository.DepartmentRepository;
import com.retailcloud.employee.repository.EmployeeRepository;
import com.retailcloud.employee.service.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department dept = new Department();
        dept.setName(request.getName());
        dept.setCreationDate(request.getCreationDate());
        Department saved = departmentRepository.save(dept);
        // set head if provided
        if (request.getHeadEmployeeId() != null) {
            Employee head = employeeRepository.findById(request.getHeadEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Head employee not found with id: " + request.getHeadEmployeeId()));
            // ensure head is assigned to this department; if not, assign and save the employee
            if (head.getDepartment() == null || !head.getDepartment().getId().equals(saved.getId())) {
                head.setDepartment(saved);
                employeeRepository.save(head);
            }
            saved.setDepartmentHead(head);
            saved = departmentRepository.save(saved);
        }
        return toResponse(saved, false);
    }

    @Override
    @Transactional
    public Optional<DepartmentResponse> updateDepartment(Long id, DepartmentRequest request) {
        return departmentRepository.findById(id).map(existing -> {
            existing.setName(request.getName());
            existing.setCreationDate(request.getCreationDate());
            if (request.getHeadEmployeeId() != null) {
                Employee head = employeeRepository.findById(request.getHeadEmployeeId())
                        .orElseThrow(() -> new ResourceNotFoundException("Head employee not found with id: " + request.getHeadEmployeeId()));
                // ensure head is assigned to this department; if not, assign and save the employee
                if (head.getDepartment() == null || !head.getDepartment().getId().equals(existing.getId())) {
                    head.setDepartment(existing);
                    employeeRepository.save(head);
                }
                existing.setDepartmentHead(head);
            } else {
                existing.setDepartmentHead(null);
            }
            Department saved = departmentRepository.save(existing);
            return toResponse(saved, false);
        });
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        long count = employeeRepository.countByDepartmentId(id);
        if (count > 0) {
            throw new IllegalStateException("Cannot delete department with employees assigned");
        }
        departmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DepartmentResponse> getDepartmentById(Long id, boolean expandEmployees) {
        return departmentRepository.findById(id).map(d -> toResponse(d, expandEmployees));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DepartmentResponse> getAllDepartments(Pageable pageable, boolean expandEmployees) {
        Page<Department> page = departmentRepository.findAll(pageable);
        List<DepartmentResponse> list = page.stream().map(d -> toResponse(d, expandEmployees)).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    private DepartmentResponse toResponse(Department dept, boolean expandEmployees) {
        DepartmentResponse resp = new DepartmentResponse();
        resp.setId(dept.getId());
        resp.setName(dept.getName());
        resp.setCreationDate(dept.getCreationDate());
        resp.setHeadEmployeeId(dept.getDepartmentHead() != null ? dept.getDepartmentHead().getId() : null);

        if (expandEmployees) {
            List<Employee> employees = employeeRepository.findByDepartmentId(dept.getId());
            List<EmployeeResponse> emps = new ArrayList<>();
            for (Employee e : employees) {
                EmployeeResponse er = new EmployeeResponse();
                er.setId(e.getId());
                er.setName(e.getName());
                er.setDob(e.getDob());
                er.setSalary(e.getSalary());
                er.setAddress(e.getAddress());
                er.setRoleTitle(e.getRoleTitle());
                er.setJoiningDate(e.getJoiningDate());
                er.setYearlyBonusPercentage(e.getYearlyBonusPercentage());
                er.setManagerId(e.getManager() != null ? e.getManager().getId() : null);
                er.setDepartmentId(e.getDepartment() != null ? e.getDepartment().getId() : null);
                emps.add(er);
            }
            resp.setEmployees(emps);
        }
        return resp;
    }
}