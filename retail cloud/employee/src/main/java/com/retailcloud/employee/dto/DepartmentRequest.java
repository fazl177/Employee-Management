package com.retailcloud.employee.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class DepartmentRequest {
    @NotBlank
    private String name;

    @NotNull
    private LocalDate creationDate;

    private Long headEmployeeId;

    public DepartmentRequest() {}

    public DepartmentRequest(String name, LocalDate creationDate, Long headEmployeeId) {
        this.name = name;
        this.creationDate = creationDate;
        this.headEmployeeId = headEmployeeId;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }
    public Long getHeadEmployeeId() { return headEmployeeId; }
    public void setHeadEmployeeId(Long headEmployeeId) { this.headEmployeeId = headEmployeeId; }
}