package com.retailcloud.employee.service.impl;

import com.retailcloud.employee.dto.EmployeeRequest;
import com.retailcloud.employee.dto.EmployeeResponse;
import com.retailcloud.employee.entity.Department;
import com.retailcloud.employee.entity.Employee;
import com.retailcloud.employee.exception.ResourceNotFoundException;
import com.retailcloud.employee.repository.DepartmentRepository;
import com.retailcloud.employee.repository.EmployeeRepository;
import com.retailcloud.employee.service.EmployeeService;
import com.retailcloud.employee.service.impl.mapper.EmployeeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee manager = null;
        // if managerId is null, allow only if there is currently no root employee
        if (request.getManagerId() == null) {
            long roots = employeeRepository.countByManagerIdIsNull();
            if (roots > 0) {
                throw new IllegalStateException("A root employee already exists; managerId is required");
            }
        } else {
            manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + request.getManagerId()));
        }
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));
        Employee entity = EmployeeMapper.toEntity(request, manager, department);
        Employee saved = employeeRepository.save(entity);
        return EmployeeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<EmployeeResponse> updateEmployee(Long id, EmployeeRequest request) {
        return employeeRepository.findById(id).map(existing -> {
            existing.setName(request.getName());
            existing.setDob(request.getDob());
            existing.setSalary(request.getSalary());
            existing.setAddress(request.getAddress());
            existing.setRoleTitle(request.getRoleTitle());
            existing.setJoiningDate(request.getJoiningDate());
            existing.setYearlyBonusPercentage(request.getYearlyBonusPercentage());
            // manager update with cycle detection and single-root enforcement
            if (request.getManagerId() != null) {
                if (request.getManagerId().equals(id)) {
                    throw new IllegalStateException("Employee cannot be their own manager");
                }
                Employee manager = employeeRepository.findById(request.getManagerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id: " + request.getManagerId()));
                // detect cycle: walk up manager chain
                Employee cur = manager;
                while (cur != null) {
                    if (cur.getId() != null && cur.getId().equals(id)) {
                        throw new IllegalStateException("Manager assignment would create a reporting cycle");
                    }
                    cur = cur.getManager();
                }
                existing.setManager(manager);
            } else {
                // setting manager to null (making this employee a root) is allowed only if there are no other roots
                long otherRoots = employeeRepository.countByManagerIdIsNullAndIdNot(id);
                if (otherRoots > 0) {
                    throw new IllegalStateException("Cannot set manager to null; another root employee exists");
                }
                existing.setManager(null);
            }
            if (request.getDepartmentId() != null) {
                Department dept = departmentRepository.findById(request.getDepartmentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));
                existing.setDepartment(dept);
            }
            Employee saved = employeeRepository.save(existing);
            return EmployeeMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeResponse> getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(EmployeeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponse> getAllEmployees(Pageable pageable, boolean lookup) {
        Page<Employee> page = employeeRepository.findAll(pageable);
        if (lookup) {
            List<EmployeeResponse> list = page.stream()
                    .map(e -> {
                        EmployeeResponse r = new EmployeeResponse();
                        r.setId(e.getId());
                        r.setName(e.getName());
                        return r;
                    })
                    .collect(Collectors.toList());
            return new PageImpl<>(list, pageable, page.getTotalElements());
        }
        List<EmployeeResponse> list = page.stream().map(EmployeeMapper::toResponse).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        // ensure employee exists
        Employee emp = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        // prevent deleting if this employee manages others
        long reports = employeeRepository.countByManagerId(id);
        if (reports > 0) {
            throw new IllegalStateException("Cannot delete employee who is a manager of other employees (reports count: " + reports + ")");
        }

        // prevent deleting if this employee is set as department head
        long heads = departmentRepository.countByDepartmentHeadId(id);
        if (heads > 0) {
            throw new IllegalStateException("Cannot delete employee who is a head of one or more departments (departments count: " + heads + ")");
        }

        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void moveEmployee(Long employeeId, Long newDepartmentId) {
        Employee emp = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        Department dept = departmentRepository.findById(newDepartmentId).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + newDepartmentId));
        emp.setDepartment(dept);
        employeeRepository.save(emp);
    }
}