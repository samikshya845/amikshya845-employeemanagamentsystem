package com.example.employee_management_system.dto;

import lombok.Data;
import java.util.List;

@Data
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long departmentId;
    private Long roleId;
    private List<Long> projectIds;
}