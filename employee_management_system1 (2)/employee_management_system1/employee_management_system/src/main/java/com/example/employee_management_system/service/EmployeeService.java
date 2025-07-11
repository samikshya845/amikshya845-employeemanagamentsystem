package com.example.employee_management_system.service;

import com.example.employee_management_system.dto.EmployeeDTO;
import com.example.employee_management_system.entity.Employee;
import com.example.employee_management_system.entity.Department;
import com.example.employee_management_system.entity.Role;
import com.example.employee_management_system.entity.Project;
import com.example.employee_management_system.repository.EmployeeRepository;
import com.example.employee_management_system.repository.DepartmentRepository;
import com.example.employee_management_system.repository.RoleRepository;
import com.example.employee_management_system.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());

        if (employeeDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            employee.setDepartment(department);
        }

        if (employeeDTO.getRoleId() != null) {
            Role role = roleRepository.findById(employeeDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            employee.setRole(role);
        }

        if (employeeDTO.getProjectIds() != null) {
            List<Project> projects = employeeDTO.getProjectIds().stream()
                    .map(id -> projectRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Project not found")))
                    .collect(Collectors.toList());
            employee.setProjects(projects);
        }

        employee = employeeRepository.save(employee);
        return mapToDTO(employee);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());

        if (employeeDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            employee.setDepartment(department);
        }

        if (employeeDTO.getRoleId() != null) {
            Role role = roleRepository.findById(employeeDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            employee.setRole(role);
        }

        if (employeeDTO.getProjectIds() != null) {
            List<Project> projects = employeeDTO.getProjectIds().stream()
                    .map(projectId -> projectRepository.findById(projectId)
                            .orElseThrow(() -> new RuntimeException("Project not found")))
                    .collect(Collectors.toList());
            employee.setProjects(projects);
        }

        employee = employeeRepository.save(employee);
        return mapToDTO(employee);
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return mapToDTO(employee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setDepartmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null);
        dto.setRoleId(employee.getRole() != null ? employee.getRole().getId() : null);
        dto.setProjectIds(employee.getProjects().stream().map(Project::getId).collect(Collectors.toList()));
        return dto;
    }
   
}