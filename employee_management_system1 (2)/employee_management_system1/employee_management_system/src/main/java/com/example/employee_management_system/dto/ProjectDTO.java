package com.example.employee_management_system.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private List<Long> employeeIds;
}