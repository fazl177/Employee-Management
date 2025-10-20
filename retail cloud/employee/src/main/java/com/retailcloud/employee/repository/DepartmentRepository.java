package com.retailcloud.employee.repository;

import com.retailcloud.employee.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Page<Department> findAll(Pageable pageable);

    // count departments where the given employee is set as department head
    long countByDepartmentHeadId(Long headEmployeeId);
}