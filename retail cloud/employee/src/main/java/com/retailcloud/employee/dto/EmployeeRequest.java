package com.retailcloud.employee.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class EmployeeRequest {
    @NotBlank
    private String name;

    @NotNull
    private LocalDate dob;

    @NotNull
    @PositiveOrZero
    private Double salary;

    @NotBlank
    private String address;

    @NotBlank
    private String roleTitle;

    @NotNull
    private LocalDate joiningDate;

    @NotNull
    @Min(0)
    @Max(100)
    private Double yearlyBonusPercentage;

    private Long managerId;

    @NotNull
    private Long departmentId;

    public EmployeeRequest() {}

    public EmployeeRequest(String name, LocalDate dob, Double salary, String address, String roleTitle, LocalDate joiningDate, Double yearlyBonusPercentage, Long managerId, Long departmentId) {
        this.name = name;
        this.dob = dob;
        this.salary = salary;
        this.address = address;
        this.roleTitle = roleTitle;
        this.joiningDate = joiningDate;
        this.yearlyBonusPercentage = yearlyBonusPercentage;
        this.managerId = managerId;
        this.departmentId = departmentId;
    }

    // getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getRoleTitle() { return roleTitle; }
    public void setRoleTitle(String roleTitle) { this.roleTitle = roleTitle; }
    public LocalDate getJoiningDate() { return joiningDate; }
    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }
    public Double getYearlyBonusPercentage() { return yearlyBonusPercentage; }
    public void setYearlyBonusPercentage(Double yearlyBonusPercentage) { this.yearlyBonusPercentage = yearlyBonusPercentage; }
    public Long getManagerId() { return managerId; }
    public void setManagerId(Long managerId) { this.managerId = managerId; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
}