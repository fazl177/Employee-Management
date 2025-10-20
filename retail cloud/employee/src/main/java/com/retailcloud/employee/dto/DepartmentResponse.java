package com.retailcloud.employee.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class DepartmentResponse {
    private Long id;
    private String name;
    private LocalDate creationDate;
    private Long headEmployeeId;
    private List<EmployeeResponse> employees; // present only when expanded

    public DepartmentResponse() {}

    public DepartmentResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }
    public Long getHeadEmployeeId() { return headEmployeeId; }
    public void setHeadEmployeeId(Long headEmployeeId) { this.headEmployeeId = headEmployeeId; }
    public List<EmployeeResponse> getEmployees() { return employees; }
    public void setEmployees(List<EmployeeResponse> employees) { this.employees = employees; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepartmentResponse)) return false;
        DepartmentResponse that = (DepartmentResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name); }
}