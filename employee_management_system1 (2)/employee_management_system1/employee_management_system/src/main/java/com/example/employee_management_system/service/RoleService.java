package com.example.employee_management_system.service;

import com.example.employee_management_system.dto.RoleDTO;
import com.example.employee_management_system.entity.Role;
import com.example.employee_management_system.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role = roleRepository.save(role);
        return mapToDTO(role);
    }

    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        role.setName(roleDTO.getName());
        role = roleRepository.save(role);
        return mapToDTO(role);
    }

    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return mapToDTO(role);
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    private RoleDTO mapToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }
}  
