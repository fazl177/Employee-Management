package com.retailcloud.employee.repository;

import com.retailcloud.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentId(Long departmentId);
    long countByDepartmentId(Long departmentId);
    Page<Employee> findAll(Pageable pageable);
    long countByManagerId(Long managerId);
    long countByManagerIdIsNull();
    long countByManagerIdIsNullAndIdNot(Long id);
}