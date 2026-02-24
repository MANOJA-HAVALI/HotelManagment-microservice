package com.auth.service.service;

import com.auth.service.dto.CreateModuleRequest;
import com.auth.service.dto.ModuleDto;
import com.auth.service.dto.UpdateModuleRequest;

import java.util.List;

public interface ModuleService {
    
    ModuleDto createModule(CreateModuleRequest request);
    
    List<ModuleDto> getAllModules();
    
    ModuleDto getModuleById(String id);
    
    ModuleDto getModuleByName(String moduleName);
    
    ModuleDto updateModule(String id, UpdateModuleRequest request);
    
    void deleteModule(String id);
    
    ModuleDto activateModule(String id);
    
    ModuleDto deactivateModule(String id);
}
