package com.auth.service.dto;

import lombok.Data;

@Data
public class ModuleDto {
    private String id;
    private String moduleName;
    private String description;
    private Boolean isActive;
}
