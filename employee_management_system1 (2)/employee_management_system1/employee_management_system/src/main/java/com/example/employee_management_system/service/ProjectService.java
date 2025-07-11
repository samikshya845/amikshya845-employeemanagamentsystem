package com.example.employee_management_system.service;

import com.example.employee_management_system.dto.ProjectDTO;
import com.example.employee_management_system.entity.Project;
import com.example.employee_management_system.entity.Employee;
import com.example.employee_management_system.repository.ProjectRepository;
import com.example.employee_management_system.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());

        if (projectDTO.getEmployeeIds() != null) {
            List<Employee> employees = projectDTO.getEmployeeIds().stream()
                    .map(id -> employeeRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Employee not found")))
                    .collect(Collectors.toList());
            project.setEmployees(employees);
        }

        project = projectRepository.save(project);
        return mapToDTO(project);
    }

    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());

        if (projectDTO.getEmployeeIds() != null) {
            List<Employee> employees = projectDTO.getEmployeeIds().stream()
                    .map(employeeId -> employeeRepository.findById(employeeId)
                            .orElseThrow(() -> new RuntimeException("Employee not found")))
                    .collect(Collectors.toList());
            project.setEmployees(employees);
        }

        project = projectRepository.save(project);
        return mapToDTO(project);
    }

    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return mapToDTO(project);
    }
public List<ProjectDTO> getAllProjects() {
    List<Project> projects = projectRepository.findAll();
    System.out.println("Projects from DB: " + projects); // ✅ Check if data is fetched
    return projects.stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
}
private ProjectDTO mapToDTO(Project project) {
    ProjectDTO dto = new ProjectDTO();
    dto.setId(project.getId());
    dto.setName(project.getName());
    dto.setDescription(project.getDescription());
    // Temporarily disable employees mapping to test
    // dto.setEmployeeIds(project.getEmployees() != null ?
    //                   project.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()) :
    //                   List.of());
    dto.setEmployeeIds(List.of()); // empty list for now
    return dto;
}

   

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

}
