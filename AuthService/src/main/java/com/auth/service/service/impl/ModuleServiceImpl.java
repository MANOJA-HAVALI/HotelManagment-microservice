package com.auth.service.service.impl;

import com.auth.service.dto.CreateModuleRequest;
import com.auth.service.dto.ModuleDto;
import com.auth.service.dto.UpdateModuleRequest;
import com.auth.service.entities.Module;
import com.auth.service.exception.ModuleAlreadyExistsException;
import com.auth.service.exception.ModuleNotFoundException;
import com.auth.service.repository.ModuleRepository;
import com.auth.service.service.ModuleService;
import com.auth.service.util.SecurityUtils;
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
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    @Override
    public ModuleDto createModule(CreateModuleRequest request) {
        if (moduleRepository.existsByModuleName(request.getModuleName())) {
            throw new ModuleAlreadyExistsException("Module with name '" + request.getModuleName() + "' already exists");
        }

        Module module = Module.builder()
                .id(UUID.randomUUID().toString())
                .moduleName(request.getModuleName())
                .description(request.getDescription())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();

        Module savedModule = moduleRepository.save(module);
        return convertToDto(savedModule);
    }

    @Override
    @Transactional(readOnly = true) // This method will only READ data, not UPDATE/INSERT/DELETE
    public List<ModuleDto> getAllModules() {
        List<Module> modules = moduleRepository.findAll();
        return modules.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleDto getModuleById(String id) {
        Optional<Module> module = moduleRepository.findById(id);
        if (!module.isPresent()) {
            throw new ModuleNotFoundException("Module not found");
        }
        return convertToDto(module.get());
    }

    @Override
    @Transactional(readOnly = true)
    public ModuleDto getModuleByName(String moduleName) {
        Optional<Module> module = moduleRepository.findByModuleName(moduleName);
        if (!module.isPresent()) {
            throw new ModuleNotFoundException("Module not found");
        }
        return convertToDto(module.get());
    }

    @Override
    public ModuleDto updateModule(String id, UpdateModuleRequest request) {
        Optional<Module> existingModuleOpt = moduleRepository.findById(id);
        if (!existingModuleOpt.isPresent()) {
            throw new ModuleNotFoundException("Module not found");
        }

        Module module = existingModuleOpt.get();

        // Check if module name is being updated and if it already exists
        if (request.getModuleName() != null && !request.getModuleName().equals(module.getModuleName())) {
            if (moduleRepository.existsByModuleName(request.getModuleName())) {
                throw new ModuleAlreadyExistsException("Module with name '" + request.getModuleName() + "' already exists");
            }
            module.setModuleName(request.getModuleName());
        }

        if (request.getDescription() != null) {
            module.setDescription(request.getDescription());
        }

        if (request.getIsActive() != null) {
            module.setIsActive(request.getIsActive());
        }

        Module updatedModule = moduleRepository.save(module);
        return convertToDto(updatedModule);
    }

    @Override
    public void deleteModule(String id) {
        if (!moduleRepository.existsById(id)) {
            throw new ModuleNotFoundException("Module not found");
        }
        moduleRepository.deleteById(id);
    }

    @Override
    public ModuleDto activateModule(String id) {
        Optional<Module> moduleOpt = moduleRepository.findById(id);
        if (!moduleOpt.isPresent()) {
            throw new ModuleNotFoundException("Module not found");
        }

        Module module = moduleOpt.get();
        module.setIsActive(true);
        Module updatedModule = moduleRepository.save(module);
        return convertToDto(updatedModule);
    }

    @Override
    public ModuleDto deactivateModule(String id) {
        Optional<Module> moduleOpt = moduleRepository.findById(id);
        if (!moduleOpt.isPresent()) {
            throw new ModuleNotFoundException("Module not found");
        }

        Module module = moduleOpt.get();
        module.setIsActive(false);
        Module updatedModule = moduleRepository.save(module);
        return convertToDto(updatedModule);
    }

    private ModuleDto convertToDto(Module module) {
        ModuleDto dto = new ModuleDto();
        dto.setId(module.getId());
        dto.setModuleName(module.getModuleName());
        dto.setDescription(module.getDescription());
        dto.setIsActive(module.getIsActive());
        return dto;
    }
}
