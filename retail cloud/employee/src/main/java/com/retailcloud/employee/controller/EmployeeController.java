package com.retailcloud.employee.controller;

import com.retailcloud.employee.dto.EmployeeRequest;
import com.retailcloud.employee.dto.EmployeeResponse;
import com.retailcloud.employee.dto.PageResponse;
import com.retailcloud.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse resp = employeeService.createEmployee(request);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeRequest request) {
        return employeeService.updateEmployee(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<PageResponse<EmployeeResponse>> getAll(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "20") int size,
                                                           @RequestParam(defaultValue = "false") boolean lookup) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeResponse> result = employeeService.getAllEmployees(pageable, lookup);
        PageResponse<EmployeeResponse> resp = toPageResponse(result);
        return ResponseEntity.ok(resp);
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<Void> moveEmployee(@PathVariable Long id, @RequestParam Long newDepartmentId) {
        employeeService.moveEmployee(id, newDepartmentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/department")
    public ResponseEntity<Void> updateEmployeeDepartmentPatch(@PathVariable Long id, @RequestParam Long departmentId) {
        employeeService.moveEmployee(id, departmentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/department")
    public ResponseEntity<Void> updateEmployeeDepartmentPut(@PathVariable Long id, @RequestParam Long departmentId) {
        employeeService.moveEmployee(id, departmentId);
        return ResponseEntity.ok().build();
    }

    private PageResponse<EmployeeResponse> toPageResponse(Page<EmployeeResponse> page) {
        PageResponse<EmployeeResponse> p = new PageResponse<>();
        p.setContent(page.getContent());
        p.setCurrentPage(page.getNumber());
        p.setPageSize(page.getSize());
        p.setTotalElements(page.getTotalElements());
        p.setTotalPages(page.getTotalPages());
        return p;
    }
}