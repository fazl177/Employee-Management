package com.retailcloud.employee.dto;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeeResponse {
    private Long id;
    private String name;
    private LocalDate dob;
    private Double salary;
    private String address;
    private String roleTitle;
    private LocalDate joiningDate;
    private Double yearlyBonusPercentage;
    private Long managerId;
    private Long departmentId;
    // friendly names for client UI
    private String managerName;
    private String departmentName;

    public EmployeeResponse() {}

    public EmployeeResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public String getManagerName() { return managerName; }
    public void setManagerName(String managerName) { this.managerName = managerName; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeResponse)) return false;
        EmployeeResponse that = (EmployeeResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name); }
}