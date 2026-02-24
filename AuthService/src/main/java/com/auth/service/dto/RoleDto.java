package com.auth.service.dto;

import lombok.Data;

@Data
public class RoleDto {
    private String id;
    private String roleName;
    private String description;
    private Boolean isActive;
}
