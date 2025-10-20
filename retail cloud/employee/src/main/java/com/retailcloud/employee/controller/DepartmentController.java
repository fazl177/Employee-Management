package com.retailcloud.employee.controller;

import com.retailcloud.employee.dto.DepartmentRequest;
import com.retailcloud.employee.dto.DepartmentResponse;
import com.retailcloud.employee.dto.PageResponse;
import com.retailcloud.employee.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        DepartmentResponse resp = departmentService.createDepartment(request);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request) {
        return departmentService.updateDepartment(id, request).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getById(@PathVariable Long id, @RequestParam(required = false) String expand) {
        boolean expandemployee = "employee".equalsIgnoreCase(expand) || "employees".equalsIgnoreCase(expand);
        return departmentService.getDepartmentById(id, expandemployee).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<PageResponse<DepartmentResponse>> getAll(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "20") int size,
                                                           @RequestParam(required = false) String expand) {
        Pageable pageable = PageRequest.of(page, size);
        boolean expandemployee = "employee".equalsIgnoreCase(expand) || "employees".equalsIgnoreCase(expand);
        Page<DepartmentResponse> result = departmentService.getAllDepartments(pageable, expandemployee);
        PageResponse<DepartmentResponse> resp = toPageResponse(result);
        return ResponseEntity.ok(resp);
    }

    private PageResponse<DepartmentResponse> toPageResponse(Page<DepartmentResponse> page) {
        PageResponse<DepartmentResponse> p = new PageResponse<>();
        p.setContent(page.getContent());
        p.setCurrentPage(page.getNumber());
        p.setPageSize(page.getSize());
        p.setTotalElements(page.getTotalElements());
        p.setTotalPages(page.getTotalPages());
        return p;
    }
}