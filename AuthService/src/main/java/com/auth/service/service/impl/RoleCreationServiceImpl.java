package com.auth.service.service.impl;

import com.auth.service.dto.CreateRoleRequest;
import com.auth.service.dto.RoleDto;
import com.auth.service.dto.UpdateRoleRequest;
import com.auth.service.entities.Role;
import com.auth.service.exception.RoleAlreadyExistsException;
import com.auth.service.exception.RoleNotFoundException;
import com.auth.service.repository.RoleRepository;
import com.auth.service.service.RoleCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleCreationServiceImpl implements RoleCreationService {

    private final RoleRepository roleRepository;

    @Override
    public RoleDto createRole(CreateRoleRequest request) {
        if (roleRepository.existsByRoleName(request.getRoleName())) {
            throw new RoleAlreadyExistsException("Role with name '" + request.getRoleName() + "' already exists");
        }

        Role role = Role.builder()
                .id(UUID.randomUUID().toString())
                .roleName(request.getRoleName())
                .description(request.getDescription())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();

        Role savedRole = roleRepository.save(role);
        return convertToDto(savedRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDto getRoleById(String id) {
        Optional<Role> role = roleRepository.findById(id);
        if (!role.isPresent()) {
            throw new RoleNotFoundException("Role not found");
        }
        return convertToDto(role.get());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDto getRoleByName(String roleName) {
        Optional<Role> role = roleRepository.findByRoleName(roleName);
        if (!role.isPresent()) {
            throw new RoleNotFoundException("Role not found");
        }
        return convertToDto(role.get());
    }

    @Override
    public RoleDto updateRole(String id, UpdateRoleRequest request) {
        Optional<Role> existingRoleOpt = roleRepository.findById(id);
        if (!existingRoleOpt.isPresent()) {
            throw new RoleNotFoundException("Role not found");
        }

        Role role = existingRoleOpt.get();

        // Check if role name is being updated and if it already exists
        if (request.getRoleName() != null && !request.getRoleName().equals(role.getRoleName())) {
            if (roleRepository.existsByRoleName(request.getRoleName())) {
                throw new RoleAlreadyExistsException("Role with name '" + request.getRoleName() + "' already exists");
            }
            role.setRoleName(request.getRoleName());
        }

        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }

        if (request.getIsActive() != null) {
            role.setIsActive(request.getIsActive());
        }

        Role updatedRole = roleRepository.save(role);
        return convertToDto(updatedRole);
    }

    @Override
    public void deleteRole(String id) {
        if (!roleRepository.existsById(id)) {
            throw new RoleNotFoundException("Role not found");
        }
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDto activateRole(String id) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException("Role not found");
        }

        Role role = roleOpt.get();
        role.setIsActive(true);
        Role updatedRole = roleRepository.save(role);
        return convertToDto(updatedRole);
    }

    @Override
    public RoleDto deactivateRole(String id) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException("Role not found");
        }

        Role role = roleOpt.get();
        role.setIsActive(false);
        Role updatedRole = roleRepository.save(role);
        return convertToDto(updatedRole);
    }

    private RoleDto convertToDto(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setDescription(role.getDescription());
        dto.setIsActive(role.getIsActive());
        return dto;
    }
}
